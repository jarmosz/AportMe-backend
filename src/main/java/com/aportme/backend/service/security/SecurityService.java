package com.aportme.backend.service.security;

import com.aportme.backend.entity.User;
import com.aportme.backend.entity.dto.TokenPairDTO;
import com.aportme.backend.entity.dto.UserLoginDTO;
import com.aportme.backend.exception.security.RefreshTokenHasExpiredException;
import com.aportme.backend.exception.security.TokenDoesNotExsistsException;
import com.aportme.backend.exception.security.WrongPasswordException;
import com.aportme.backend.security.SecurityProperties;
import com.aportme.backend.security.TokenType;
import com.aportme.backend.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class SecurityService {

    @Value("${jwt.encryption.secret}")
    private String secret;

    @Value("${jwt.refresh.token.expiration.seconds}")
    private int refreshTokenExpirationTime;
    @Value("${jwt.auth.token.expiration.seconds}")
    private int authTokenExpirationTime;

    private final SecurityProperties securityProperties;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;


    public TokenPairDTO loginUser(UserLoginDTO userLoginDTO) {
        String userEmail = userLoginDTO.getEmail();
        User user = userService.findByEmail(userEmail);
        return checkUserPassword(userLoginDTO, user);
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
        String authToken = generateAuthToken(user);
        String refreshToken = generateRefreshToken(user);
        return new TokenPairDTO(authToken, refreshToken);
    }

    @Transactional
    public TokenPairDTO refreshAccessToken(HttpServletRequest request) {
        String token = extractToken(request);
        if (token == null) {
            throw new TokenDoesNotExsistsException();
        }
        DecodedJWT refreshToken = JWT.require(Algorithm.HMAC256(secret.getBytes()))
                .build()
                .verify(token);

        if (refreshToken.getExpiresAt().after(new Date())) {
            long userId = refreshToken.getClaim("id").asLong();
            User user = userService.findById(userId);
            return recreateTokens(user);
        } else {
            throw new RefreshTokenHasExpiredException();
        }
    }

    private TokenPairDTO recreateTokens(User user) {
        String authToken = generateAuthToken(user);
        String refreshToken = generateRefreshToken(user);
        return new TokenPairDTO(authToken, refreshToken);
    }

    public String generateAuthToken(User user) {
        return createTokenByType(user, authTokenExpirationTime, TokenType.AUTH_TOKEN);
    }

    public String generateRefreshToken(User user) {
        return createTokenByType(user, refreshTokenExpirationTime, TokenType.REFRESH_TOKEN);
    }

    private String createTokenByType(User user, long expirationTimeInSeconds, TokenType tokenType) {
        long expirationDateInMillis = System.currentTimeMillis() + expirationTimeInSeconds * 1000;
        Date expirationDate = new Date(expirationDateInMillis);
        switch (tokenType) {
            case AUTH_TOKEN:
                return JWT.create()
                        .withSubject(String.valueOf(user.getEmail()))
                        .withIssuedAt(new Date())
                        .withExpiresAt(expirationDate)
                        .withClaim("id", user.getId())
                        .withClaim("role", user.getRole().toString())
                        .sign(Algorithm.HMAC256(securityProperties.getSecret().getBytes()));
            case REFRESH_TOKEN:
                return JWT.create()
                        .withSubject(String.valueOf(user.getEmail()))
                        .withIssuedAt(new Date())
                        .withExpiresAt(expirationDate)
                        .withClaim("id", user.getId())
                        .sign(Algorithm.HMAC256(securityProperties.getSecret().getBytes()));
        }
        return "";
    }

    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader(securityProperties.getHeaderString());
        if (authHeader.startsWith(securityProperties.getTokenType())) {
            authHeader = authHeader.substring(securityProperties.getTokenType().length());
            return authHeader.trim();
        }
        return null;
    }
}
