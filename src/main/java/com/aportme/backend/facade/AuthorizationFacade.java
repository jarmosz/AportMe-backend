package com.aportme.backend.facade;

import com.aportme.backend.entity.User;
import com.aportme.backend.entity.dto.TokenPairDTO;
import com.aportme.backend.entity.dto.UserLoginDTO;
import com.aportme.backend.entity.dto.user.AuthUserDTO;
import com.aportme.backend.entity.dto.user.ChangeUserPasswordDTO;
import com.aportme.backend.exception.UserAlreadyExistsException;
import com.aportme.backend.exception.WrongChangePasswordDataException;
import com.aportme.backend.exception.WrongUserCredentialsException;
import com.aportme.backend.exception.security.RefreshTokenHasExpiredException;
import com.aportme.backend.exception.security.TokenDoesNotExsistsException;
import com.aportme.backend.security.SecurityProperties;
import com.aportme.backend.service.UserService;
import com.aportme.backend.service.security.SecurityService;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@Transactional
@RequiredArgsConstructor
public class AuthorizationFacade {

    @Value("${jwt.refresh.token.expiration.seconds}")
    private int refreshTokenExpirationTime;
    @Value("${jwt.auth.token.expiration.seconds}")
    private int authTokenExpirationTime;

    private final SecurityProperties securityProperties;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final SecurityService securityService;

    public TokenPairDTO loginUser(UserLoginDTO userLoginDTO) {
        String userEmail = userLoginDTO.getEmail();
        User user = userService.findByEmail(userEmail);
        return securityService.checkUserPassword(userLoginDTO, user);
    }

    public TokenPairDTO refreshAccessToken(HttpServletRequest request) {
        String token = securityService.extractToken(request);
        if (token == null) {
            throw new TokenDoesNotExsistsException();
        }

        DecodedJWT refreshToken = securityService.verifyToken(token);

        if (refreshToken.getExpiresAt().after(new Date())) {
            Long userId = refreshToken.getClaim("id").asLong();
            User user = userService.findById(userId);
            return securityService.recreateTokens(user);
        }
        throw new RefreshTokenHasExpiredException();
    }

    public TokenPairDTO registerUser(AuthUserDTO userDTO) {
        if (securityService.validateData(userDTO)) {
            Boolean isUserRegistered = userService.isUserExists(userDTO.getEmail());

            if (isUserRegistered) {
                throw new UserAlreadyExistsException();
            }
            User user = userService.mapToUser(userDTO);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.saveUser(user);
            return securityService.createTokenPair(user);
        } else {
            throw new WrongUserCredentialsException();
        }
    }

    public void changeUserPassword(ChangeUserPasswordDTO passwords) {
        User loggedUser = userService.getLoggedUser();
        String encodedNewPassword = passwordEncoder.encode(passwords.getNewPassword());
        if (securityService.isNewPasswordDataValid(passwords, loggedUser)) {
            loggedUser.setPassword(encodedNewPassword);
            userService.saveUser(loggedUser);
        } else {
            throw new WrongChangePasswordDataException();
        }
    }

}