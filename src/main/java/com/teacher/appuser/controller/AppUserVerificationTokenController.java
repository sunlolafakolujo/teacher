package com.teacher.verificationtoken.controller;

import com.teacher.appuser.model.AppUser;
import com.teacher.event.RegistrationCompleteEvent;
import com.teacher.verificationtoken.exception.VerificationTokeNotFoundException;
import com.teacher.verificationtoken.model.VerificationToken;
import com.teacher.verificationtoken.service.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "api/teacher/appUser/")
@RequiredArgsConstructor
public class VerificationTokenController {
    private final VerificationTokenService verificationTokenService;
    private final ApplicationEventPublisher publisher;

    @GetMapping("/verifyAccount/{token}")
    public String verifyAccount(@PathVariable(value = "token") String token) throws VerificationTokeNotFoundException {
        String verificationToken=verificationTokenService.validateVerificationToken(token);
        if (verificationToken.equalsIgnoreCase("valid")){
            return "Registration Successfully";
        }
        return "Bad User";
    }

    @GetMapping("resendVerificationToken/{token}")
    public String resendNewVerificationToken(@PathVariable(value = "token") String oldToken, HttpServletRequest request){
        VerificationToken verificationToken=verificationTokenService.generateNewVerificationToken(oldToken);
        AppUser appUser=verificationToken.getAppUser();
        publisher.publishEvent(new RegistrationCompleteEvent(appUser, applicationUrl(request)));
        return "Verification link sent";
    }

    private String applicationUrl(HttpServletRequest request){
        return "http://" + request.getServerName()+":"+ request.getServerPort()+ request.getContextPath();
    }
}
