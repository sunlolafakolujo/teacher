package com.teacher.appuser.controller;

import com.teacher.appuser.model.*;
import com.teacher.appuser.service.AppUserService;
import com.teacher.event.RegistrationCompleteEvent;
import com.teacher.resetpassword.model.PasswordModel;
import com.teacher.staticdata.UserType;
import com.teacher.userrole.model.UserRole;
import com.teacher.userrole.service.UserRoleService;
import com.teacher.verificationtoken.model.VerificationToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/appUser")
@RequiredArgsConstructor
@Slf4j
public class AppUserRestController {

    private final AppUserService appUserService;

    private final UserRoleService userRoleService;

    private final ModelMapper modelMapper;

    private final ApplicationEventPublisher applicationEventPublisher;

    @PostMapping("/schoolRegistration")
    public ResponseEntity<SchoolDto> registerUser(@RequestBody @Valid SchoolDto schoolDto,
                                                  final HttpServletRequest httpServletRequest){

        schoolDto.setUserType(UserType.SCHOOL);

        List<UserRole> userRoles=new ArrayList<>();

        UserRole userRole=userRoleService.findUserByName("SCHOOL");

        userRoles.add(userRole);

        schoolDto.setUserRoles(userRoles);

        AppUser appUser=modelMapper.map(schoolDto, AppUser.class);

        AppUser post=appUserService.userRegistration(appUser);

        SchoolDto posted=modelMapper.map(post, SchoolDto.class);

        applicationEventPublisher.publishEvent(new RegistrationCompleteEvent(post, applicationUrl(httpServletRequest)));

        log.info("Registration Successful");

        return new ResponseEntity<>(posted, HttpStatus.CREATED);
    }

    @PostMapping("/teacherRegistration")
    public  ResponseEntity<TeacherDto> teacherRegistration(@Valid @RequestBody TeacherDto teacherDto,
                                                           final HttpServletRequest httpServletRequest){
        teacherDto.setUserType(UserType.TEACHER);

        List<UserRole> userRoles =new ArrayList<>();

        UserRole userRole=userRoleService.findUserByName("TEACHER");

        userRoles.add(userRole);

        teacherDto.setUserRoles(userRoles);

        AppUser appUser=modelMapper.map(teacherDto, AppUser.class);

        AppUser post=appUserService.userRegistration(appUser);

        TeacherDto posted=modelMapper.map(post, TeacherDto.class);

        applicationEventPublisher.publishEvent(new RegistrationCompleteEvent(post, applicationUrl(httpServletRequest)));

        log.info("Registration Successful");

        return new ResponseEntity<>(posted, HttpStatus.CREATED);
    }

    @PostMapping("/parentRegistration")
    public ResponseEntity<ParentDto> parentRegistration(@Valid @RequestBody ParentDto parentDto,
                                                        final HttpServletRequest httpServletRequest){

        parentDto.setUserType(UserType.PARENT);

        List<UserRole> userRoles=new ArrayList<>();

        UserRole userRole=userRoleService.findUserByName("PARENT");

        userRoles.add(userRole);

        parentDto.setUserRoles(userRoles);

        AppUser appUser=modelMapper.map(parentDto, AppUser.class);

        AppUser post= appUserService.userRegistration(appUser);

        ParentDto posted=modelMapper.map(post, ParentDto.class);

        applicationEventPublisher.publishEvent(new RegistrationCompleteEvent(post, applicationUrl(httpServletRequest)));

        log.info("Registration Successful");

        return new ResponseEntity<>(posted, HttpStatus.CREATED);
    }

    @PostMapping("resetPassword")
    public String resetPassword(@RequestBody PasswordModel passwordModel, HttpServletRequest httpServletRequest){

        AppUser appUser=appUserService.findUserByUsername(passwordModel.getUsername());
        String url="";

        if (appUser !=null){
            String token= UUID.randomUUID().toString();

            appUserService.createPasswordTokenForUser(appUser,token);

            url=passwordResetTokenMail(appUser,applicationUrl(httpServletRequest),token);
        }

        return url;
    }

    @PostMapping("/savePassword")
    public String savePassword(@RequestParam("token") String token, @RequestBody PasswordModel passwordModel){

        String result=appUserService.validatePasswordRestToken(token);

        if (!result.equalsIgnoreCase("valid")){
            return "Invalid Token";
        }

        Optional<AppUser> appUser= appUserService.getUserByPasswordResetToken(token);

        if (appUser.isPresent()){
            appUserService.changePassword(appUser.get(),passwordModel.getNewPassword());
            return "Password reset Successfully";
        }else return "Invalid Token";
    }

    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token){
        String result =appUserService.validateVerificationToken(token);
        if(result.equalsIgnoreCase("valid")){
            return "User Verified Successfully";
        }
        return "Bad User";
    }

    @GetMapping("/resendVerificationToken")
    public String resendVerificationToken(@RequestParam("token") String oldToken, HttpServletRequest httpServletRequest){
        VerificationToken verificationToken=appUserService.generateNewVerificationToken(oldToken);

        AppUser appUser=verificationToken.getAppUser();

        resendVerificationTokenMail(appUser, applicationUrl(httpServletRequest), verificationToken);

        return "Verification Link Sent";
    }




































    private void resendVerificationTokenMail(AppUser appUser, String applicationUrl, VerificationToken verificationToken) {
        String url=
                applicationUrl + "/verifyRegistration?token=" + verificationToken.getToken();

        log.info("click the link to verify your account: {}", url);
    }

    private String applicationUrl(HttpServletRequest httpServletRequest){
        return "http://" + httpServletRequest.getServerName() +
                ":" +
                httpServletRequest.getServerPort() +
                httpServletRequest.getContextPath();
    }

    private String passwordResetTokenMail(AppUser appUser, String applicationUrl, String token) {

        String url=
                applicationUrl + "/savePassword?token=" + token;

        log.info("click the link to Reset your password: {}", url);

        return url;
    }

}
