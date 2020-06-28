package com.aportme.backend.component.activationToken.listener;

import com.aportme.backend.component.activationToken.event.OnRegistrationCompleteEvent;
import com.aportme.backend.component.activationToken.service.ActivationEmailBuilderService;
import com.aportme.backend.component.activationToken.service.ActivationTokenService;
import com.aportme.backend.component.mailSender.service.MailSenderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ActivationTokenListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    public ActivationTokenListener(
            ActivationTokenService activationTokenService,
            ActivationEmailBuilderService activationEmailBuilderService,
            MailSenderService mailSenderService,
            PasswordEncoder passwordEncoder) {
        this.activationTokenService = activationTokenService;
        this.activationEmailBuilderService = activationEmailBuilderService;
        this.mailSenderService = mailSenderService;
        this.passwordEncoder = passwordEncoder;
    }

    private ActivationTokenService activationTokenService;
    private ActivationEmailBuilderService activationEmailBuilderService;
    private MailSenderService mailSenderService;
    private PasswordEncoder passwordEncoder;

    @Value("${activation.token.url}")
    private String activationTokenUrl;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent onRegistrationCompleteEvent) {
        this.confirmRegistration(onRegistrationCompleteEvent);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        String recipient = event.getUser().getEmail();
        String token = passwordEncoder.encode(UUID.randomUUID().toString());
        activationTokenService.saveToken(event.getUser(), token);
        String confirmationUrl = activationTokenUrl + token;
        mailSenderService.sendActivationMail(recipient, "Aktywuj swoje konto w aplikacji",
                activationEmailBuilderService.build(confirmationUrl));
    }
}
