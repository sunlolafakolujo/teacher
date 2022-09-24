package com.teacher.verificationtoken.controller;

import com.teacher.appuser.model.AppUser;
import com.teacher.verificationtoken.exception.VerificationTokeNotFoundException;
import com.teacher.verificationtoken.model.VerificationToken;
import com.teacher.verificationtoken.service.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "api/verification")
@RequiredArgsConstructor
public class VerificationTokenController {
    private final VerificationTokenService verificationTokenService;

    @GetMapping("/verifyAccount/{token}")
    public String verifyAccount(@PathVariable(value = "token") String token) throws VerificationTokeNotFoundException {
        String verificationToken=verificationTokenService.validateVerificationToken(token);
        if (verificationToken.equalsIgnoreCase("Valid")){
            return "User Registered Successfully";
        }
        return "Bad User";
    }

    @GetMapping("resendVerificationToken/{token}")
    public String resendNewVerificationToken(@PathVariable(value = "token") String token, HttpServletRequest request){
        VerificationToken verificationToken=verificationTokenService.generateNewVerificationToken(token);
        AppUser appUser=verificationToken.getAppUser();
        generateNewVerificationTokenEmail(applicationUrl(request),appUser,verificationToken);
        return "Verification link sent";
    }

    private SimpleMailMessage generateNewVerificationTokenEmail(String applicationUrl, AppUser appUser, VerificationToken verificationToken) {
        String link=applicationUrl+"/api/appUser/verifyAccount/"+verificationToken.getToken();
        String message="Dear "+appUser.getUsername()+",\r\n"+"Click on the link for get new verification token"+link;
        String subject="Resend Verification Token";
        return newVerificationEmail( subject,message, appUser);
    }

    private SimpleMailMessage newVerificationEmail(String subject, String text, AppUser appUser){

        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setTo(appUser.getEmail());
        mailMessage.setFrom("fakolujos@gmail.com");
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        return mailMessage;
    }

    private String applicationUrl(HttpServletRequest request){
        return "http://" + request.getServerName()+ request.getServerPort()+ request.getContextPath();
    }
}
