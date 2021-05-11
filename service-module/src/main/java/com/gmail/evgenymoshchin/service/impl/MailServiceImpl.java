package com.gmail.evgenymoshchin.service.impl;

import com.gmail.evgenymoshchin.service.MailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import static com.gmail.evgenymoshchin.service.constants.UserServiceConstants.MAIL_PASSWORD_CHANGE_NOTIFICATION_VALUE;
import static com.gmail.evgenymoshchin.service.constants.UserServiceConstants.MAIL_TEXT_NOTIFICATION_VALUE;

@Component
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;

    public MailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmail(String username, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(username);
        message.setSubject(MAIL_PASSWORD_CHANGE_NOTIFICATION_VALUE);
        message.setText(String.format(MAIL_TEXT_NOTIFICATION_VALUE, username, password));
        javaMailSender.send(message);
    }
}
