package com.teacher.event.listener;

import com.teacher.appuser.model.AppUser;
import com.teacher.appuser.service.AppUserService;
import com.teacher.event.RegistrationCompleteEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private AppUserService appUserService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {

        //Create the verification token for the User with link
        AppUser appUser= event.getAppUser();
        String token= UUID.randomUUID().toString();
        appUserService.saveVerificationTokenForUser(token, appUser);

        //Send mail to User
//        String url= event.getApplicationUrl() + "/verifyRegistration?token=" + token;
        String url= event.getApplicationUrl() + "/api/appUser/verifyRegistration/" + token;

        log.info("click the link to verify your account: {}", url);

    }
}
