package com.aportme.backend.facade;

import com.aportme.backend.entity.User;
import com.aportme.backend.entity.dto.TokenPairDTO;
import com.aportme.backend.entity.dto.UserLoginDTO;
import com.aportme.backend.entity.dto.user.AuthUserDTO;
import com.aportme.backend.entity.dto.user.ChangeUserPasswordDTO;
import com.aportme.backend.entity.enums.Role;
import com.aportme.backend.exception.UserAlreadyExistsException;
import com.aportme.backend.exception.WrongChangePasswordDataException;
import com.aportme.backend.exception.WrongUserCredentialsException;
import com.aportme.backend.exception.security.AccessDeniedException;
import com.aportme.backend.exception.security.RefreshTokenHasExpiredException;
import com.aportme.backend.exception.security.TokenDoesNotExsistsException;
import com.aportme.backend.service.UserService;
import com.aportme.backend.service.security.SecurityService;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@Transactional
@RequiredArgsConstructor
public class AuthorizationFacade {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final SecurityService securityService;

    public TokenPairDTO loginUser(UserLoginDTO userLoginDTO, Boolean isMobileRequest) {
        String userEmail = userLoginDTO.getEmail().toLowerCase();
        User user = userService.findByEmail(userEmail);
        if (isMobileRequest != null && isMobileRequest && user.getRole() != Role.USER) {
            throw new AccessDeniedException();
        }
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
        String userEmailInLowerCase = userDTO.getEmail().toLowerCase();
        if (securityService.validateData(userDTO)) {
            Boolean isUserRegistered = userService.isUserExists(userEmailInLowerCase);

            if (isUserRegistered) {
                throw new UserAlreadyExistsException();
            }
            User user = User.builder()
                    .email(userDTO.getEmail().toLowerCase())
                    .role(Role.USER)
                    .password(passwordEncoder.encode(userDTO.getPassword()))
                    .build();
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
