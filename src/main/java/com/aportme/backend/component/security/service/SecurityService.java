package com.aportme.backend.component.security.service;

import com.aportme.backend.component.security.config.JWTAuthentication;
import com.aportme.backend.component.security.config.JWTAuthorizationFilter;
import com.aportme.backend.component.security.dto.AccessTokenDTO;
import com.aportme.backend.component.security.dto.TokenPairDTO;
import com.aportme.backend.component.security.dto.UserLoginDTO;
import com.aportme.backend.component.security.entity.RefreshToken;
import com.aportme.backend.component.security.exception.RefreshTokenDoesNotExistsException;
import com.aportme.backend.component.security.exception.UserDoesNotExistsException;
import com.aportme.backend.component.security.exception.WrongPasswordException;
import com.aportme.backend.component.security.repository.RefreshTokenRepository;
import com.aportme.backend.component.user.entity.User;
import com.aportme.backend.component.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SecurityService {

    private static final String HEADER_STRING = "Authorization";


    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JWTAuthorizationFilter jwtAuthorizationFilter;
    private RefreshTokenRepository refreshTokenRepository;

    private RefreshToken generateRefreshToken(User user){
        String refreshToken = RandomStringUtils.randomAlphanumeric(128);
        RefreshToken newRefreshToken = new RefreshToken();
        newRefreshToken.setRefreshToken(refreshToken);
        newRefreshToken.setExpiryDate(DateTime.now().plusMinutes(136800));
        newRefreshToken.setUser(user);
        return newRefreshToken;
    }

    public TokenPairDTO loginUser(UserLoginDTO userLoginDTO){
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

    private TokenPairDTO createTokenPair(User user){
        String accessToken = jwtAuthorizationFilter.generateAccessToken(user.getId());

        Optional<RefreshToken> refreshTokenFromDB = refreshTokenRepository.findRefreshTokenByUser(user);

        RefreshToken newRefreshToken = generateRefreshToken(user);

        if(refreshTokenFromDB.isPresent()){
            RefreshToken editRefreshToken = refreshTokenFromDB.get();
            editRefreshToken.setExpiryDate(newRefreshToken.getExpiryDate());
            editRefreshToken.setRefreshToken(newRefreshToken.getRefreshToken());
            refreshTokenRepository.save(editRefreshToken);
        }
        else {
            refreshTokenRepository.save(newRefreshToken);
        }

        return new TokenPairDTO(accessToken, newRefreshToken.getRefreshToken());
    }

    public long getLoggedUserId() {
        JWTAuthentication authentication = (JWTAuthentication) SecurityContextHolder.getContext().getAuthentication();
        return authentication.getUserId();
    }

    public AccessTokenDTO refreshAccessToken(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String token = httpRequest.getHeader(HEADER_STRING);
        String[] tokenParts = token.split(" ");
        String refreshToken = tokenParts[1];


        Optional<RefreshToken> refreshTokenFromDB = refreshTokenRepository.findByRefreshToken(refreshToken);
        if(refreshTokenFromDB.isPresent() && refreshTokenFromDB.get().getExpiryDate().isAfterNow()){
            return new AccessTokenDTO(jwtAuthorizationFilter.generateAccessToken(refreshTokenFromDB.get().getUser().getId()));
        }
        throw new RefreshTokenDoesNotExistsException();
    }


    public void logoutUser(ServletRequest request, ServletResponse response){
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String token = httpRequest.getHeader(HEADER_STRING);
        String[] tokenParts = token.split(" ");
        String mainToken = tokenParts[1];
        refreshTokenRepository.findByRefreshToken(mainToken)
                .ifPresent(refreshTokenRepository::delete);
    }

}
