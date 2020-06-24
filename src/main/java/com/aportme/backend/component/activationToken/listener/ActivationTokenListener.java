package com.aportme.backend.component.activationToken.listener;

import com.aportme.backend.component.activationToken.event.OnRegistrationCompleteEvent;
import com.aportme.backend.component.activationToken.service.ActivationEmailBuilderService;
import com.aportme.backend.component.activationToken.service.ActivationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class ActivationTokenListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private ActivationTokenService activationTokenService;
    private ActivationEmailBuilderService activationEmailBuilderService;
    private JavaMailSender mailSender;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent onRegistrationCompleteEvent) {
        this.confirmRegistration(onRegistrationCompleteEvent);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {

        String recipient = event.getUser().getEmail();
        String token = bCryptPasswordEncoder.encode(UUID.randomUUID().toString());

        activationTokenService.saveToken(event.getUser(), token);

        String confirmationUrl = event.getAppUrl() + "/activateAccount?token=" + token;

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("aportme@gmail.com");
            messageHelper.setTo(recipient);
            messageHelper.setSubject("Aktywuj swoje konto w aplikacji");
            String content = activationEmailBuilderService.build(confirmationUrl);
            messageHelper.setText(content, true);
        };
        try {
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            // runtime exception; compiler will not force you to handle it
        }
    }
}
