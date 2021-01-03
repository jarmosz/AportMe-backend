package com.aportme.backend.service.security;

import com.aportme.backend.entity.User;
import com.aportme.backend.entity.dto.TokenPairDTO;
import com.aportme.backend.entity.dto.UserLoginDTO;
import com.aportme.backend.entity.dto.user.AuthUserDTO;
import com.aportme.backend.entity.dto.user.ChangeUserPasswordDTO;
import com.aportme.backend.exception.security.WrongPasswordException;
import com.aportme.backend.security.SecurityProperties;
import com.aportme.backend.security.TokenType;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    private static final String EMAIL_REGEX_PATTERN = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";
    private static final String PASSWORD_REGEX_PATTERN = "^.{8,256}$";

    private final SecurityProperties securityProperties;
    private final PasswordEncoder passwordEncoder;

    public TokenPairDTO checkUserPassword(UserLoginDTO userLoginDTO, User user) {
        if (isPasswordMatch(userLoginDTO, user)) {
            return createTokenPair(user);
        }
        throw new WrongPasswordException();
    }

    private boolean isPasswordMatch(UserLoginDTO userLoginDTO, User user) {
        return passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword());
    }

    public TokenPairDTO createTokenPair(User user) {
        String authToken = generateAuthToken(user);
        String refreshToken = generateRefreshToken(user);
        return new TokenPairDTO(authToken, refreshToken);
    }

    public TokenPairDTO recreateTokens(User user) {
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

    public String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader(securityProperties.getHeaderString());
        if (authHeader.startsWith(securityProperties.getTokenType())) {
            authHeader = authHeader.substring(securityProperties.getTokenType().length());
            return authHeader.trim();
        }
        return null;
    }

    public DecodedJWT verifyToken(String token) {
        return JWT.require(Algorithm.HMAC256(secret.getBytes()))
                .build()
                .verify(token);
    }

    public boolean validateEmail(String email) {
        return email.matches(EMAIL_REGEX_PATTERN);
    }

    public boolean validatePassword(String password) {
        return password.matches(PASSWORD_REGEX_PATTERN);
    }

    public boolean validateData(AuthUserDTO userDTO) {
        return validateEmail(userDTO.getEmail()) && validatePassword(userDTO.getPassword());
    }

    public boolean isNewPasswordDataValid(ChangeUserPasswordDTO passwords, User loggedUser) {
        return validatePassword(passwords.getOldPassword())
                && passwordEncoder.matches(passwords.getOldPassword(), loggedUser.getPassword())
                && validatePassword(passwords.getNewPassword())
                && (passwords.getNewPassword().equals(passwords.getRepeatedNewPassword()));
    }

}
