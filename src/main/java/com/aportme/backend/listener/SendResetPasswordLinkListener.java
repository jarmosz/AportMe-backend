package com.aportme.backend.listener;

import com.aportme.backend.event.SendResetPasswordLinkEvent;
import com.aportme.backend.service.ConfirmResetPasswordEmailBuilderService;
import com.aportme.backend.service.ResetPasswordTokenService;
import com.aportme.backend.service.MailSenderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SendResetPasswordLinkListener implements ApplicationListener<SendResetPasswordLinkEvent> {

    public SendResetPasswordLinkListener(
            ResetPasswordTokenService resetPasswordTokenService,
            ConfirmResetPasswordEmailBuilderService confirmResetPasswordEmailBuilderService,
            MailSenderService mailSenderService,
            PasswordEncoder passwordEncoder) {
        this.resetPasswordTokenService = resetPasswordTokenService;
        this.confirmResetPasswordEmailBuilderService = confirmResetPasswordEmailBuilderService;
        this.mailSenderService = mailSenderService;
        this.passwordEncoder = passwordEncoder;
    }

    private ResetPasswordTokenService resetPasswordTokenService;
    private ConfirmResetPasswordEmailBuilderService confirmResetPasswordEmailBuilderService;
    private MailSenderService mailSenderService;
    private PasswordEncoder passwordEncoder;

    @Value("${frontendUrl}")
    private String frontendUrl;

    @Value("${confirm.reset.password.token.url}")
    private String confirmResetPasswordTokenUrl;

    @Override
    public void onApplicationEvent(SendResetPasswordLinkEvent sendResetPasswordLinkEvent) {
        this.sendResetPasswordLink(sendResetPasswordLinkEvent);
    }

    private void sendResetPasswordLink(SendResetPasswordLinkEvent event) {
        String recipient = event.getUser().getEmail();
        String token = passwordEncoder.encode(UUID.randomUUID().toString()).replace("/", "2");
        resetPasswordTokenService.saveToken(event.getUser(), token);
        String confirmationUrl = frontendUrl + confirmResetPasswordTokenUrl + token;
        mailSenderService.sendResetPasswordConfirmMail(recipient, "Zmiana hasła w aplikacji - AportMe",
                confirmResetPasswordEmailBuilderService.build(confirmationUrl));
    }
}

