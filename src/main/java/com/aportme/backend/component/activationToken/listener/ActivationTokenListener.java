package com.aportme.backend.component.activationToken.listener;

import com.aportme.backend.component.activationToken.event.OnRegistrationCompleteEvent;
import com.aportme.backend.component.activationToken.service.ActivationEmailBuilderService;
import com.aportme.backend.component.activationToken.service.ActivationTokenService;
import com.aportme.backend.component.mailSender.service.MailSenderService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class ActivationTokenListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private ActivationTokenService activationTokenService;
    private ActivationEmailBuilderService activationEmailBuilderService;
    private MailSenderService mailSenderService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent onRegistrationCompleteEvent) {
        this.confirmRegistration(onRegistrationCompleteEvent);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        String recipient = event.getUser().getEmail();
        String token = bCryptPasswordEncoder.encode(UUID.randomUUID().toString());
        activationTokenService.saveToken(event.getUser(), token);
        String confirmationUrl = "http://localhost:8080" + "/activateAccount?token=" + token;
        mailSenderService.sendHtmlMail(recipient, "aportme@gmail.com", "Aktywuj swoje konto w aplikacji",
                activationEmailBuilderService.build(confirmationUrl));
    }
}
