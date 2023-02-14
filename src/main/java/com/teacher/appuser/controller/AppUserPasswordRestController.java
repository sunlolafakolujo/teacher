package com.teacher.appuser.controller;

import com.teacher.appuser.exception.AppUserNotFoundException;
import com.teacher.appuser.model.AppUser;
import com.teacher.appuser.service.AppUserService;
import com.teacher.event.PasswordEvent;
import com.teacher.password.exception.PasswordNotFoundException;
import com.teacher.password.model.PasswordModel;
import com.teacher.password.service.PasswordTokenService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/teacher/appUser")
@AllArgsConstructor
public class AppUserPasswordRestController {
    private final PasswordTokenService passwordTokenService;
    private final AppUserService appUserService;
    private final ApplicationEventPublisher publisher;

    @PostMapping("/resetPassword")
    public String resetUserPassword(@RequestBody PasswordModel passwordModel,
                                    HttpServletRequest request) throws AppUserNotFoundException {

        AppUser appUser= appUserService.findByUsernameOrEmailOrMobileOrUserId(passwordModel.getUsernameOrEmailOrMobileOrUserId());

        if (appUser!=null){
            publisher.publishEvent(new PasswordEvent(applicationUrl(request), appUser));
        }else throw new AppUserNotFoundException("User Not Found");

        return "An email will be sent to you to reset your password";
    }

    @PostMapping("/savePassword/{token}")
    public String savePassword(@RequestBody @Valid PasswordModel passwordModel,
                               @PathVariable(value = "token") String token)  {

        Optional<AppUser> optionalAppUser= passwordTokenService.findUserByPasswordToken(token);

        if (optionalAppUser.isPresent()){
            passwordTokenService.changeUserPassword(optionalAppUser.get(), passwordModel.getNewPassword());
            return "Password Reset Successfully";
        }else return "Invalid Token";
    }

    @PostMapping("/changePassword")
    public String changeUserPassword(@RequestBody PasswordModel passwordModel) throws PasswordNotFoundException,
                                                                                    AppUserNotFoundException {

        AppUser appUser=appUserService.findByUsernameOrEmailOrMobileOrUserId(passwordModel.getUsernameOrEmailOrMobileOrUserId());
        if (appUser==null){
            throw new AppUserNotFoundException("User Not Found");
        }
        if (!passwordTokenService.checkIfOldPasswordExist(appUser,passwordModel.getOldPassword())){
            return "Invalid old password";
        }else passwordTokenService.changeUserPassword(appUser,passwordModel.getNewPassword() );

        return  "Password changed successfully";

    }

    private String applicationUrl(HttpServletRequest request){
        return "http://"+request.getServerName()+ ":"+request.getServerPort()+request.getContextPath();
    }
}
