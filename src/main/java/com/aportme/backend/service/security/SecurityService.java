package com.aportme.backend.service.security;

import com.aportme.backend.entity.User;
import com.aportme.backend.entity.dto.TokenPairDTO;
import com.aportme.backend.repository.UserRepository;
import com.aportme.backend.security.SecurityProperties;
import com.aportme.backend.entity.dto.UserLoginDTO;
import com.aportme.backend.entity.RefreshToken;
import com.aportme.backend.exception.security.RefreshTokenHasExpiredException;
import com.aportme.backend.exception.security.TokenDoesNotExsistsException;
import com.aportme.backend.exception.security.UserDoesNotExistsException;
import com.aportme.backend.exception.security.WrongPasswordException;
import com.aportme.backend.repository.RefreshTokenRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SecurityService {

    private final static int TOKEN_LENGTH = 128;
    private final static int REFRESH_TOKEN_LIFETIME = 136800;

    private final SecurityProperties securityProperties;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    private RefreshToken generateRefreshToken(User user) {
        String refreshToken = RandomStringUtils.randomAlphanumeric(TOKEN_LENGTH);
        DateTime expiryDate = DateTime.now().plusMinutes(REFRESH_TOKEN_LIFETIME);
        return RefreshToken.builder()
                .token(refreshToken)
                .expiryDate(expiryDate)
                .user(user)
                .build();
    }

    public TokenPairDTO loginUser(UserLoginDTO userLoginDTO) {
        return userRepository.findByEmail(userLoginDTO.getEmail())
                .map(user -> checkUserPassword(userLoginDTO, user))
                .orElseThrow(UserDoesNotExistsException::new);
    }

    private TokenPairDTO checkUserPassword(UserLoginDTO userLoginDTO, User user) {
        if (isPasswordMatch(userLoginDTO, user)) {
            return createTokenPair(user);
        } else {
            throw new WrongPasswordException();
        }
    }

    private boolean isPasswordMatch(UserLoginDTO userLoginDTO, User user) {
        return passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword());
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
        RefreshToken oldRefreshToken = refreshTokenRepository.findByToken(token).orElseThrow(TokenDoesNotExsistsException::new);
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
        long expirationDateInMillis = System.currentTimeMillis() + securityProperties.getExpirationTimeInSeconds() * 1000;
        Date expirationDate = new Date(expirationDateInMillis);
        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withIssuedAt(new Date())
                .withExpiresAt(expirationDate)
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
    public void logoutUser(HttpServletRequest request) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(extractToken(request)).orElseThrow(TokenDoesNotExsistsException::new);
        refreshTokenRepository.deleteTokenById(refreshToken.getId());
    }
}
