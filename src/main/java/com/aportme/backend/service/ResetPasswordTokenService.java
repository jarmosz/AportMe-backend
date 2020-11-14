package com.aportme.backend.service;

import com.aportme.backend.entity.ResetPasswordLink;
import com.aportme.backend.entity.User;
import com.aportme.backend.entity.dto.user.ResetUserPasswordFormDTO;
import com.aportme.backend.exception.ResetPasswordLinkTokenHasExpired;
import com.aportme.backend.exception.ResetPasswordLinkTokenNotFound;
import com.aportme.backend.exception.WrongResetPasswordDataException;
import com.aportme.backend.repository.ResetPasswordTokenRepository;
import com.aportme.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResetPasswordTokenService {

    private static final String PASSWORD_REGEX_PATTERN = "^.{8,256}$";

    @Value("${confirm.reset.password.token.expiration.seconds}")
    private int tokenExpirationTime;

    private final ResetPasswordTokenRepository resetPasswordLinkTokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void saveToken(User user, String token) {
        ResetPasswordLink resetPasswordLink = new ResetPasswordLink();
        resetPasswordLink.setUser(user);
        resetPasswordLink.setToken(token);
        resetPasswordLink.setExpiryDate(new DateTime().plusSeconds(tokenExpirationTime));

        resetPasswordLinkTokenRepository.save(resetPasswordLink);
    }

    private boolean checkIfTokenIsExpired(ResetPasswordLink token) {
        return token.getExpiryDate().isBeforeNow();
    }

    public void checkResetPasswordToken(String linkToken) {
        ResetPasswordLink resetPasswordLink = resetPasswordLinkTokenRepository
                .findResetPasswordLinkTokenByToken(linkToken)
                .orElseThrow(ResetPasswordLinkTokenNotFound::new);
        if (checkIfTokenIsExpired(resetPasswordLink)) {
            throw new ResetPasswordLinkTokenHasExpired();
        }
    }

    private boolean validatePassword(String password, String repeatPassword) {
        return password.equals(repeatPassword) && password.matches(PASSWORD_REGEX_PATTERN);
    }

    public void changeUserPassword(ResetUserPasswordFormDTO resetUserPasswordFormDTO) {
        String linkToken = resetUserPasswordFormDTO.getResetPasswordLinkToken();
        ResetPasswordLink resetPasswordLink =
                resetPasswordLinkTokenRepository.findResetPasswordLinkTokenByToken(linkToken).orElseThrow(ResetPasswordLinkTokenNotFound::new);
        if (checkIfTokenIsExpired(resetPasswordLink)) {
            throw new ResetPasswordLinkTokenHasExpired();
        }
        User user = resetPasswordLink.getUser();
        String newPassword = resetUserPasswordFormDTO.getNewPassword();
        String repeatedNewPassword = resetUserPasswordFormDTO.getRepeatedNewPassword();
        if (validatePassword(newPassword, repeatedNewPassword)) {
            String encodedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodedPassword);
            userRepository.save(user);
            resetPasswordLinkTokenRepository.delete(resetPasswordLink);
        } else {
            throw new WrongResetPasswordDataException();
        }
    }
}