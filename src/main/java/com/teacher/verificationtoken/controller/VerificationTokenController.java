package com.teacher.verificationtoken.controller;

import com.teacher.appuser.model.AppUser;
import com.teacher.event.RegistrationCompleteEvent;
import com.teacher.verificationtoken.exception.VerificationTokeNotFoundException;
import com.teacher.verificationtoken.model.VerificationToken;
import com.teacher.verificationtoken.service.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "api/teacher")
@RequiredArgsConstructor
public class VerificationTokenController {
    private final VerificationTokenService verificationTokenService;
    private final ApplicationEventPublisher publisher;

    @GetMapping("/verifyAccount")
    public String verifyAccount(@RequestParam("token") String token) throws VerificationTokeNotFoundException {
        String verificationToken=verificationTokenService.validateVerificationToken(token);
        if (verificationToken.equalsIgnoreCase("valid")){
            return "Registration Successfully";
        }
        return "Bad User";
    }

    @GetMapping("resendVerificationToken")
    public String resendNewVerificationToken(@RequestParam("token") String oldToken, HttpServletRequest request){
        VerificationToken verificationToken=verificationTokenService.generateNewVerificationToken(oldToken);
        AppUser appUser=verificationToken.getAppUser();
        publisher.publishEvent(new RegistrationCompleteEvent(appUser, applicationUrl(request)));
        return "Verification link sent";
    }

    private String applicationUrl(HttpServletRequest request){
        return "http://" + request.getServerName()+":"+ request.getServerPort()+ request.getContextPath();
    }
}
