package com.aportme.backend.component.activationToken.listener;

import com.aportme.backend.component.activationToken.event.OnRegistrationCompleteEvent;
import com.aportme.backend.component.activationToken.repository.ActivationTokenRepository;
import com.aportme.backend.component.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ActivationTokenListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private MessageSource messages;
    private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent onRegistrationCompleteEvent) {
        this.confirmRegistration(onRegistrationCompleteEvent);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo("jarmoszw@gmail.com");
        email.setSubject("Testowyy");
        email.setText("Hello test!");
        mailSender.send(email);
    }
}
