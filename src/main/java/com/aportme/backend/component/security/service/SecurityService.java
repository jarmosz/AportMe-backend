package com.aportme.backend.component.security.service;

import com.aportme.backend.component.security.config.SecurityProperties;
import com.aportme.backend.component.security.dto.TokenPairDTO;
import com.aportme.backend.component.security.dto.UserLoginDTO;
import com.aportme.backend.component.security.entity.RefreshToken;
import com.aportme.backend.component.security.exception.RefreshTokenHasExpiredException;
import com.aportme.backend.component.security.exception.TokenDoesNotExsistsException;
import com.aportme.backend.component.security.exception.UserDoesNotExistsException;
import com.aportme.backend.component.security.exception.WrongPasswordException;
import com.aportme.backend.component.security.repository.RefreshTokenRepository;
import com.aportme.backend.component.user.entity.User;
import com.aportme.backend.component.user.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SecurityService {

    private SecurityProperties securityProperties;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RefreshTokenRepository refreshTokenRepository;

    private RefreshToken generateRefreshToken(User user) {
        String refreshToken = RandomStringUtils.randomAlphanumeric(128);
        RefreshToken newRefreshToken = new RefreshToken();
        newRefreshToken.setToken(refreshToken);
        newRefreshToken.setExpiryDate(DateTime.now().plusMinutes(136800));
        newRefreshToken.setUser(user);
        return newRefreshToken;
    }

    public TokenPairDTO loginUser(UserLoginDTO userLoginDTO) {
        return userRepository.findByEmail(userLoginDTO.getEmail())
                .map(user -> {
                    if (passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
                        return createTokenPair(user);
                    } else {
                        throw new WrongPasswordException();
                    }
                })
                .orElseThrow(UserDoesNotExistsException::new);
    }

    private TokenPairDTO createTokenPair(User user) {
        String accessToken = generateAccessToken(user.getId());
        Optional<RefreshToken> refreshTokenFromDB = refreshTokenRepository.findTokenByUser(user);
        RefreshToken newRefreshToken = generateRefreshToken(user);
        if (refreshTokenFromDB.isPresent()) {
            RefreshToken editRefreshToken = refreshTokenFromDB.get();
            editRefreshToken.setExpiryDate(newRefreshToken.getExpiryDate());
            editRefreshToken.setToken(newRefreshToken.getToken());
            refreshTokenRepository.save(editRefreshToken);
        } else {
            refreshTokenRepository.save(newRefreshToken);
        }
        return new TokenPairDTO(accessToken, newRefreshToken.getToken());
    }

    @Transactional
    public TokenPairDTO refreshAccessToken(HttpServletRequest request) {
        String token = extractToken(request);
        if (token == null) {
            throw new TokenDoesNotExsistsException();
        }
        RefreshToken oldRefreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(TokenDoesNotExsistsException::new);
        if (oldRefreshToken.getExpiryDate().isAfterNow()) {
            return recreateTokens(oldRefreshToken);
        } else {
            throw new RefreshTokenHasExpiredException();
        }
    }

    private TokenPairDTO recreateTokens(RefreshToken oldRefreshToken) {
        User user = oldRefreshToken.getUser();
        RefreshToken newRefreshToken = generateRefreshToken(user);
        refreshTokenRepository.deleteTokenById(oldRefreshToken.getId());
        refreshTokenRepository.save(newRefreshToken);
        String token = generateAccessToken(user.getId());
        String refreshToken = newRefreshToken.getToken();
        return new TokenPairDTO(token, refreshToken);
    }

    public String generateAccessToken(long userId) {
        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withIssuedAt(new Date())
                .withExpiresAt(
                        new Date(System.currentTimeMillis()
                                + securityProperties.getExpirationTimeInSeconds() * 1000)
                )
                .sign(Algorithm.HMAC256(securityProperties.getSecret().getBytes()));
    }

    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader(securityProperties.getHeaderString());
        if (authHeader.startsWith(securityProperties.getTokenType())) {
            authHeader = authHeader.substring(securityProperties.getTokenType().length());
            return authHeader.trim();
        }
        return null;
    }

    @Transactional
    public ResponseEntity logoutUser(HttpServletRequest request) {
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByToken(extractToken(request));
        if(refreshToken.isPresent()){
            refreshTokenRepository.deleteTokenById(refreshToken.get().getId());
            return new ResponseEntity<>("User logged out.", HttpStatus.NO_CONTENT);
        }
        else {
            throw new TokenDoesNotExsistsException();
        }
    }

}
