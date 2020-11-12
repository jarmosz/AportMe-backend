package com.aportme.backend.service;

import com.aportme.backend.entity.ResetPasswordLinkToken;
import com.aportme.backend.entity.User;
import com.aportme.backend.entity.dto.user.ResetUserPasswordFormDTO;
import com.aportme.backend.exception.ResetPasswordLinkTokenHasExpired;
import com.aportme.backend.exception.ResetPasswordLinkTokenNotFound;
import com.aportme.backend.exception.WrongResetPasswordDataException;
import com.aportme.backend.repository.ResetPasswordTokenRepository;
import com.aportme.backend.repository.UserRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordTokenService {

    @Value("${confirm.reset.password.token.expiration.seconds}")
    private int tokenExpirationTime;

    private final ResetPasswordTokenRepository resetPasswordLinkTokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String PASSWORD_REGEX_PATTERN = "^.{8,256}$";

    public ResetPasswordTokenService(ResetPasswordTokenRepository resetPasswordLinkTokenRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.resetPasswordLinkTokenRepository = resetPasswordLinkTokenRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public void saveToken(User user, String token) {
        ResetPasswordLinkToken resetPasswordLinkToken = new ResetPasswordLinkToken();
        resetPasswordLinkToken.setUser(user);
        resetPasswordLinkToken.setToken(token);
        resetPasswordLinkToken.setExpiryDate(new DateTime().plusSeconds(tokenExpirationTime));

        resetPasswordLinkTokenRepository.save(resetPasswordLinkToken);
    }

    private boolean checkIfTokenIsExpired(ResetPasswordLinkToken token) {
        return token.getExpiryDate().isBeforeNow();
    }

    public void checkResetPasswordToken(String linkToken) {
        ResetPasswordLinkToken resetPasswordLinkToken = resetPasswordLinkTokenRepository
                .findResetPasswordLinkTokenByToken(linkToken)
                .orElseThrow(ResetPasswordLinkTokenNotFound::new);
        if (checkIfTokenIsExpired(resetPasswordLinkToken)) {
            throw new ResetPasswordLinkTokenHasExpired();
        }
    }

    private boolean validatePassword(String password, String repeatPassword) {
        return password.equals(repeatPassword) && password.matches(PASSWORD_REGEX_PATTERN);
    }


    public void changeUserPassword(ResetUserPasswordFormDTO resetUserPasswordFormDTO) {
        String linkToken = resetUserPasswordFormDTO.getResetPasswordLinkToken();
        ResetPasswordLinkToken resetPasswordLinkToken =
                resetPasswordLinkTokenRepository.findResetPasswordLinkTokenByToken(linkToken).orElseThrow(ResetPasswordLinkTokenNotFound::new);
        if (checkIfTokenIsExpired(resetPasswordLinkToken)) {
            throw new ResetPasswordLinkTokenHasExpired();
        }
        User user = resetPasswordLinkToken.getUser();
        String newPassword = resetUserPasswordFormDTO.getNewPassword();
        String repeatNewPassword = resetUserPasswordFormDTO.getRepeatNewPassword();
        if(validatePassword(newPassword, repeatNewPassword)){
            String encodedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodedPassword);
            userRepository.save(user);
            resetPasswordLinkTokenRepository.delete(resetPasswordLinkToken);
        }
        else {
            throw new WrongResetPasswordDataException();
        }

    }
}