package com.teacher.event.listener;

import com.teacher.appuser.model.AppUser;
import com.teacher.appuser.service.AppUserService;
import com.teacher.event.RegistrationCompleteEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {

        this.confirmRegistration(event);
    }

    private void confirmRegistration(RegistrationCompleteEvent event){

        AppUser appUser= event.getAppUser();

        String token= UUID.randomUUID().toString();

        appUserService.saveVerificationTokenForUser(token, appUser);

        SimpleMailMessage email = constructEmailMessage(event, appUser, token);

        this.javaMailSender.send(email);
    }

    private SimpleMailMessage constructEmailMessage(RegistrationCompleteEvent event,  AppUser appUser, String token) {

        String recipientAddress = appUser.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl = event.getApplicationUrl() + "/api/appUser/verifyRegistration/" + token;

        SimpleMailMessage email = new SimpleMailMessage();

        email.setTo(recipientAddress);
        email.setFrom("springboot.email.senders@gmail.com");
        email.setSubject(subject);
        email.setText(confirmationUrl);

        return email;
    }
}
