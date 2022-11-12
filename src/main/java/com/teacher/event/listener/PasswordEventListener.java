package com.teacher.event.listener;

import com.teacher.applicationform.model.ApplicationForm;
import com.teacher.appuser.model.AppUser;
import com.teacher.emailconfig.EmailConfiguration;
import com.teacher.event.PasswordEvent;
import com.teacher.password.service.PasswordTokenService;
import com.teacher.vacancy.model.Vacancy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class PasswordEventListener implements ApplicationListener<PasswordEvent> {

    @Autowired
    private PasswordTokenService passwordTokenService;

    @Autowired
    private EmailConfiguration emailConfiguration;

    @Override
    public void onApplicationEvent(PasswordEvent event) {
        JavaMailSenderImpl mailSender=new JavaMailSenderImpl();
        mailSender.setHost(emailConfiguration.getHost());
        mailSender.setPort(emailConfiguration.getPort());
        mailSender.setUsername(emailConfiguration.getUsername());
        mailSender.setPassword(emailConfiguration.getPassword());

        AppUser appUser=event.getAppUser();
        String token= UUID.randomUUID().toString();

        passwordTokenService.createPasswordResetTokenForUser(token, appUser);
        SimpleMailMessage message= constructPasswordTokenEmail(event,appUser,token);
        mailSender.send(message);
    }

    private SimpleMailMessage constructPasswordTokenEmail(PasswordEvent event, AppUser appUser, String token) {

        String link=event.getApplicationUrl() +"/api/teacher/appUser/savePassword/"+token;
        String to= appUser.getEmail();
        String from="fakolujos@gmail.com";
        String subject="Reset Password";
        String text="Dear "+appUser.getUsername()+",\r\n"+"Click on the link to reset password "+link;

        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(from);
        message.setSubject(subject);
        message.setText(text);
        return message;
    }
}
