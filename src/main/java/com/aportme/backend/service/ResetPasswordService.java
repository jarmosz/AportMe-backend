package com.aportme.backend.service;

import com.aportme.backend.entity.ResetPasswordLink;
import com.aportme.backend.entity.User;
import com.aportme.backend.entity.dto.user.ResetUserPasswordDTO;
import com.aportme.backend.entity.dto.user.ResetUserPasswordFormDTO;
import com.aportme.backend.event.SendResetPasswordLinkEvent;
import com.aportme.backend.exception.ResetPasswordLinkTokenHasExpired;
import com.aportme.backend.exception.ResetPasswordLinkTokenNotFound;
import com.aportme.backend.exception.UserNotFoundException;
import com.aportme.backend.exception.WrongResetPasswordDataException;
import com.aportme.backend.repository.ResetPasswordTokenRepository;
import com.aportme.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@Transactional
@RequiredArgsConstructor
public class ResetPasswordService {

    private static final String PASSWORD_REGEX_PATTERN = "^.{8,256}$";

    @Value("${confirm.reset.password.token.expiration.seconds}")
    private int tokenExpirationTime;

    private final ResetPasswordTokenRepository resetPasswordLinkTokenRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void sendResetPasswordEmail(ResetUserPasswordDTO resetUserPasswordDTO) {
        String userEmail = resetUserPasswordDTO.getEmail().toLowerCase();
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        eventPublisher.publishEvent(new SendResetPasswordLinkEvent(user, LocalDate.now()));
    }

    public void saveToken(User user, String token) {
        LocalDateTime expiration = LocalDateTime.now().plus(tokenExpirationTime, ChronoUnit.SECONDS);
        ResetPasswordLink resetPasswordLink = ResetPasswordLink.builder()
                .user(user)
                .token(token)
                .expiryDate(expiration)
                .build();

        resetPasswordLinkTokenRepository.save(resetPasswordLink);
    }

    private boolean isValidToken(ResetPasswordLink token) {
        return token.getExpiryDate().isBefore(LocalDateTime.now());
    }

    public void checkResetPasswordToken(String linkToken) {
        ResetPasswordLink resetPasswordLink = findByToken(linkToken);

        if (isValidToken(resetPasswordLink)) {
            throw new ResetPasswordLinkTokenHasExpired();
        }
    }

    private boolean isNewPasswordValid(ResetUserPasswordFormDTO dto) {
        String newPassword = dto.getNewPassword();
        String repeatedPassword = dto.getRepeatedNewPassword();

        return newPassword.equals(repeatedPassword) && newPassword.matches(PASSWORD_REGEX_PATTERN);
    }

    public void changeUserPassword(ResetUserPasswordFormDTO dto) {
        String token = dto.getResetPasswordLinkToken();
        ResetPasswordLink resetPasswordLink = findByToken(token);
        if (isValidToken(resetPasswordLink)) {
            throw new ResetPasswordLinkTokenHasExpired();
        }

        User user = resetPasswordLink.getUser();
        if (isNewPasswordValid(dto)) {
            updatePassword(dto, user);
            deleteResetToken(resetPasswordLink);
        } else {
            throw new WrongResetPasswordDataException();
        }
    }

    private void updatePassword(ResetUserPasswordFormDTO dto, User user) {
        String encodedPassword = passwordEncoder.encode(dto.getNewPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    private void deleteResetToken(ResetPasswordLink resetToken) {
        resetPasswordLinkTokenRepository.delete(resetToken);
    }

    public ResetPasswordLink findByToken(String token) {
        return resetPasswordLinkTokenRepository.findByToken(token).orElseThrow(ResetPasswordLinkTokenNotFound::new);
    }
}