package com.aportme.backend.listener;

import com.aportme.backend.event.SendResetPasswordLinkEvent;
import com.aportme.backend.service.ConfirmResetPasswordEmailBuilderService;
import com.aportme.backend.service.ResetPasswordService;
import com.aportme.backend.service.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SendResetPasswordLinkListener implements ApplicationListener<SendResetPasswordLinkEvent> {

    private final ResetPasswordService resetPasswordService;
    private final ConfirmResetPasswordEmailBuilderService confirmResetPasswordEmailBuilderService;
    private final MailSenderService mailSenderService;
    private final PasswordEncoder passwordEncoder;

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
        resetPasswordService.saveToken(event.getUser(), token);
        String confirmationUrl = String.format("%s%s%s", frontendUrl, confirmResetPasswordTokenUrl, token);
        mailSenderService.sendResetPasswordConfirmMail(recipient, "Zmiana hasła w aplikacji - AportMe",
                confirmResetPasswordEmailBuilderService.build(confirmationUrl));
    }
}

