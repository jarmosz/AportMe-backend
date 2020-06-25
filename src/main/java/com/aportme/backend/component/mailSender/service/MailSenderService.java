package com.aportme.backend.component.mailSender.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {

    private JavaMailSender mailSender;

    public MailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Value("${spring.mail.username}")
    private String mailSenderAddress;

    public void sendActivationMail(String receiver, String subject, String content) {
        try {
            MimeMessagePreparator messagePreparator = prepareMail(receiver, subject, content);
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            //TODO
        }
    }

    private MimeMessagePreparator prepareMail(String receiver, String subject, String content) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(receiver);
            messageHelper.setFrom(mailSenderAddress);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);
        };
    }
}
