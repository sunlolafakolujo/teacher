package com.teacher.appuser.controller;

import com.teacher.appuser.exception.AppUserNotFoundException;
import com.teacher.appuser.model.AppUser;
import com.teacher.appuser.service.AppUserService;
import com.teacher.password.exception.PasswordNotFoundException;
import com.teacher.password.model.PasswordModel;
import com.teacher.password.service.PasswordTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/appUser")
@RequiredArgsConstructor
public class AppUserPasswordRestController {
    private final PasswordTokenService passwordTokenService;
    private final AppUserService appUserService;

    @PostMapping("/savePassword")
    public String savePassword(@RequestBody @Valid PasswordModel passwordModel) throws PasswordNotFoundException {
        String result=passwordTokenService.validatePasswordToken(passwordModel.getToken());

        if (!result.equalsIgnoreCase("Valid")){
            throw new PasswordNotFoundException("Invalid Token");
        }
        Optional<AppUser> optionalAppUser= passwordTokenService.findUserByPasswordToken(passwordModel.getToken());

        if (optionalAppUser.isPresent()){
            passwordTokenService.changeUserPassword(optionalAppUser.get(), passwordModel.getNewPassword());
        }
        return "Password is saved successfully";
    }

    @PostMapping("/resetPassword")
    public String resetAppUserPassword(@RequestBody PasswordModel passwordModel,
                                       HttpServletRequest request) throws AppUserNotFoundException {

        AppUser appUser= appUserService.findUserByUsername(passwordModel.getUsername());

        if (appUser!=null){
            String token=UUID.randomUUID().toString();
            passwordTokenService.savePasswordTokenForUser(token, appUser);
            sendPasswordResetEmail(applicationUrl(request), appUser, token);
        }

        return "An email will be sent to you to reset your password";
    }

    @PostMapping("/changePassword")
    public String changeUserPassword(@RequestBody PasswordModel passwordModel) throws PasswordNotFoundException,
                                                                                    AppUserNotFoundException {

        AppUser appUser=appUserService.findUserByUsername(passwordModel.getUsername());
        if (!passwordTokenService.checkIfOldPasswordExist(appUser,passwordModel.getOldPassword())){
            throw new PasswordNotFoundException("Invalid old password");
        }
        passwordTokenService.changeUserPassword(appUser,passwordModel.getNewPassword() );
        return "Password changed successfully";
    }

    private String applicationUrl(HttpServletRequest request){
        return "http://"+request.getServerName()+request.getServerPort()+request.getContextPath();
    }

    private SimpleMailMessage sendPasswordResetEmail(String applicationUrl, AppUser appUser, String token){
        String link= applicationUrl+"/api/"+token;
        String message="Dear "+appUser.getUsername()+",\n\n"+"Click on the link to reset password"+link;
        String subject="Reset Password";
        SimpleMailMessage mailMessage=constructPasswordEmail(subject, message, appUser);
        return mailMessage;
    }

    private SimpleMailMessage constructPasswordEmail(String subject, String text, AppUser appUser){
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setSubject(subject);
        mailMessage.setTo(appUser.getEmail());
        mailMessage.setFrom("fakolujos@gmail.com");
        mailMessage.setText(text);
        return mailMessage;
    }
}
