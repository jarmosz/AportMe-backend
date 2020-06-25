package com.aportme.backend.component.mailSender.service;

import lombok.AllArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailSenderService {

    private JavaMailSender mailSender;

    public void sendHtmlMail(String recipient, String sender, String subject, String htmlContent){
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(recipient);
            messageHelper.setFrom(sender);
            messageHelper.setSubject(subject);
            messageHelper.setText(htmlContent, true);
        };
        try {
            mailSender.send(messagePreparator);
        } catch (MailException e) {

        }
    }
}
