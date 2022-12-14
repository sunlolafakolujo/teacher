package com.teacher.event.listener;

import com.teacher.appuser.model.AppUser;
import com.teacher.appuser.service.AppUserService;
import com.teacher.emailconfig.EmailConfiguration;
import com.teacher.event.RegistrationCompleteEvent;
import com.teacher.verificationtoken.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private EmailConfiguration emailConfiguration;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        JavaMailSenderImpl mailSender=new JavaMailSenderImpl();
        mailSender.setHost(emailConfiguration.getHost());
        mailSender.setPort(emailConfiguration.getPort());
        mailSender.setUsername(emailConfiguration.getUsername());
        mailSender.setPassword(emailConfiguration.getPassword());

        AppUser appUser= event.getAppUser();
        String token= UUID.randomUUID().toString();
        verificationTokenService.saveVerificationTokenForUser(token, appUser);
        SimpleMailMessage mail= constructEmailMessage(event, appUser, token);
        mailSender.send(mail);
    }


    private SimpleMailMessage constructEmailMessage(RegistrationCompleteEvent event,  AppUser appUser, String token) {

        SimpleMailMessage mailMessage=new SimpleMailMessage();
        String confirmUrl=event.getApplicationUrl() + "/api/teacher/appUser/verifyAccount/"+token;
        String receiver= appUser.getEmail();
        String subject="Verify Account";
        String from="fakolujos@gmail.com";

        mailMessage.setTo(receiver);
        mailMessage.setFrom(from);
        mailMessage.setSentDate(new Date());
        mailMessage.setSubject(subject);
        mailMessage.setText("Dear "+appUser.getFirstName()+",\n\n"+"Click the link to verify email "+confirmUrl);

        return mailMessage;
    }
}
