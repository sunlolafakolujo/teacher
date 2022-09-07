package com.teacher.appuser.controller;

import com.teacher.appuser.exception.AppUserNotFoundException;
import com.teacher.appuser.model.*;
import com.teacher.appuser.service.AppUserService;
import com.teacher.event.RegistrationCompleteEvent;
import com.teacher.password.exception.PasswordNotFoundException;
import com.teacher.password.model.PasswordModel;
import com.teacher.staticdata.UserType;
import com.teacher.userrole.model.UserRole;
import com.teacher.userrole.service.UserRoleService;
import com.teacher.verificationtoken.model.VerificationToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

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
                                                  final HttpServletRequest httpServletRequest)
                                                  throws AppUserNotFoundException {

        schoolDto.setUserType(UserType.SCHOOL);

        List<UserRole> userRoles=new ArrayList<>();

        UserRole userRole=userRoleService.findUserRoleByName("SCHOOL");

        userRoles.add(userRole);

        schoolDto.setUserRoles(userRoles);

        schoolDto.setUserId("SCH-".concat(UUID.randomUUID().toString()));

        AppUser appUser=modelMapper.map(schoolDto, AppUser.class);

        AppUser post=appUserService.userRegistration(appUser);

        SchoolDto posted=modelMapper.map(post, SchoolDto.class);

        applicationEventPublisher.publishEvent(new RegistrationCompleteEvent(post, applicationUrl(httpServletRequest)));

        log.info("Registration Successful");

        return new ResponseEntity<>(posted, HttpStatus.CREATED);
    }

    @PostMapping("/teacherRegistration")
    public  ResponseEntity<TeacherDto> teacherRegistration(@Valid @RequestBody TeacherDto teacherDto,
                                                           final HttpServletRequest httpServletRequest)
                                                            throws AppUserNotFoundException {

        teacherDto.setUserType(UserType.TEACHER);

        List<UserRole> userRoles =new ArrayList<>();

        UserRole userRole=userRoleService.findUserRoleByName("TEACHER");

        userRoles.add(userRole);

        teacherDto.setUserRoles(userRoles);

        teacherDto.setUserId("TCH-".concat(UUID.randomUUID().toString()));

        teacherDto.setAge(Period.between(teacherDto.getDateOfBirth(),LocalDate.now()).getYears());

        AppUser appUser=modelMapper.map(teacherDto, AppUser.class);

        AppUser post=appUserService.userRegistration(appUser);

        TeacherDto posted=modelMapper.map(post, TeacherDto.class);

        applicationEventPublisher.publishEvent(new RegistrationCompleteEvent(post, applicationUrl(httpServletRequest)));

        log.info("Registration Successful");

        return new ResponseEntity<>(posted, HttpStatus.CREATED);
    }

    @PostMapping("/parentRegistration")
    public ResponseEntity<ParentDto> parentRegistration(@Valid @RequestBody ParentDto parentDto,
                                                        final HttpServletRequest httpServletRequest)
                                                        throws AppUserNotFoundException {

        parentDto.setUserType(UserType.PARENT);

        List<UserRole> userRoles=new ArrayList<>();

        UserRole userRole=userRoleService.findUserRoleByName("PARENT");

        userRoles.add(userRole);

        parentDto.setUserRoles(userRoles);

        parentDto.setUserId("PAR-".concat(UUID.randomUUID().toString()));

        AppUser appUser=modelMapper.map(parentDto, AppUser.class);

        AppUser post= appUserService.userRegistration(appUser);

        ParentDto posted=modelMapper.map(post, ParentDto.class);

        applicationEventPublisher.publishEvent(new RegistrationCompleteEvent(post, applicationUrl(httpServletRequest)));

        log.info("Registration Successful");

        return new ResponseEntity<>(posted, HttpStatus.CREATED);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordModel passwordModel,
                                HttpServletRequest httpServletRequest) throws AppUserNotFoundException {

        AppUser appUser=appUserService.findUserByUsername(passwordModel.getUsername());

        if (appUser !=null){
            String token= UUID.randomUUID().toString();

            appUserService.savePasswordTokenForUser(appUser,token);

           passwordResetTokenMail(applicationUrl(httpServletRequest),token,appUser);
        }

        return ResponseEntity.ok("You should receive an Password Reset Email shortly");
    }

    @PostMapping("/savePassword")
    public String savePassword(@RequestBody PasswordModel passwordModel) {

        String result=appUserService.validatePasswordResetToken(passwordModel.getToken());

        if (!result.equalsIgnoreCase("valid")){
            return "Invalid Token";
        }

        Optional<AppUser> appUser= appUserService.getUserByPasswordResetToken(passwordModel.getToken());

        if (appUser.isPresent()){

            appUserService.changePassword(appUser.get(),passwordModel.getNewPassword());

            return "Password reset Successfully";

        }else return "Invalid Token";
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestBody PasswordModel passwordModel) throws AppUserNotFoundException {

        AppUser appUser=appUserService.findUserByUsername(passwordModel.getUsername());

        if (!appUserService.checkIfOldPassword(appUser, passwordModel.getOldPassword())){

            throw new AppUserNotFoundException("Invalid Old Password");
        }

        return"Password changed successfully";
    }

    @GetMapping("/verifyRegistration/{token}")
    public String verifyRegistration(@PathVariable(value="token") String token){
        String result =appUserService.validateVerificationToken(token);

        if(result.equalsIgnoreCase("valid")){
            return "User Verified Successfully";
        }

        return "Bad User";
    }

    @GetMapping("/resendVerificationToken/{token}")
    public String resendVerificationToken(@PathVariable(value = "token") String oldToken, HttpServletRequest httpServletRequest){
        VerificationToken verificationToken=appUserService.generateNewVerificationToken(oldToken);

        AppUser appUser=verificationToken.getAppUser();

        resendVerificationTokenMail(applicationUrl(httpServletRequest),verificationToken, appUser);

        return "Verification Link Sent";
    }

    @GetMapping("/findUserById/{id}")
    public ResponseEntity<AppUserDto> getUserById(@PathVariable(value = "id") Long id) throws AppUserNotFoundException {

        AppUser appUser=appUserService.findUserById(id);

        AppUserDto appUserDto=convertAppUserToDto(appUser);

        return new ResponseEntity<>(appUserDto, HttpStatus.OK);
    }

    @GetMapping("/findAllUsers")
    public ResponseEntity<List<AppUserDto>> getAllUsers(){
        Pageable pageable= PageRequest.of(0, 10);

        return new ResponseEntity<>(appUserService.findAllUsers(pageable)
                .stream()
                .map(this::convertAppUserToDto)
                .collect(Collectors.toList()),HttpStatus.OK);
    }

    @GetMapping("/findUserByUsername/{username}")
    public ResponseEntity<AppUserDto> getUserByUsername(@PathVariable(value = "username") String username) throws AppUserNotFoundException {
        AppUser appUser=appUserService.findUserByUsername(username);

        AppUserDto appUserDto=convertAppUserToDto(appUser);

        return new ResponseEntity<>(appUserDto, HttpStatus.OK);
    }

    @GetMapping("/findUserByFirstName/{firstName}")
    public ResponseEntity<List<AppUserDto>> getUserByFirstName(@PathVariable(value = "firstName") String firstName){
        Pageable pageable=PageRequest.of(0, 10);

        return new ResponseEntity<>(appUserService.findUserByFirstName(firstName, pageable)
                .stream()
                .map(this::convertAppUserToDto)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/findUserByLastName/{lastName}")
    public ResponseEntity<List<AppUserDto>> getUserByLastName(@PathVariable(value = "lastName") String lastName){
        Pageable pageable=PageRequest.of(0, 10);

        return new ResponseEntity<>(appUserService.findUserByLastName(lastName, pageable)
                .stream()
                .map(this::convertAppUserToDto)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/findUserBySchoolName/{schoolName}")
    public ResponseEntity<List<AppUserDto>> getUserBySchoolName(@PathVariable(value = "schoolName") String schoolName){

        return new ResponseEntity<>(appUserService.findBySchoolName(schoolName)
                .stream()
                .map(this::convertAppUserToDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }


    private String applicationUrl(HttpServletRequest httpServletRequest){
        return "http://"+ httpServletRequest.getServerName() +
                ":" +
                httpServletRequest.getServerPort() +
                httpServletRequest.getContextPath();
    }

    private SimpleMailMessage resendVerificationTokenMail(String applicationUrl, VerificationToken verificationToken, AppUser appUser) {

        String url= applicationUrl + "/api/appUser/verifyRegistration/" + verificationToken.getToken();

        String message ="We will send an email with a new registration token to your email account";

        return constructEmail("Resend Registration Token", message + " \r\n" + url, appUser);
    }

    private SimpleMailMessage passwordResetTokenMail(String applicationUrl,String token, AppUser appUser) {
        final String url = applicationUrl + "/api/appUser/savePassword/" + token;
        final String message ="click the link";
        return constructEmail("Reset Password", message + " \r\n" + url, appUser);
    }

    private SimpleMailMessage constructEmail(String subject, String body, AppUser appUser) {
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(appUser.getEmail());
        email.setFrom("springboot.email.senders@gmail.com");
        return email;
    }

    private AppUserDto convertAppUserToDto(AppUser appUser){

        AppUserDto appUserDto=new AppUserDto();

        appUserDto.setUserId(appUser.getUserId());
        appUserDto.setUserType(appUser.getUserType());
        appUserDto.setUsername(appUser.getUsername());
        appUserDto.setPassword(appUser.getPassword());
        appUserDto.setEmail(appUser.getEmail());
        appUserDto.setAge(appUserDto.getAge());
        appUserDto.setMeansOfIdentification(appUser.getMeansOfIdentification());
        appUserDto.setMeansOfIdentificationIssueDate(appUser.getMeansOfIdentificationIssueDate());
        appUserDto.setMeansOfIdentificationExpiryDate(appUser.getMeansOfIdentificationExpiryDate());
        appUserDto.setDateOfBirth(appUser.getDateOfBirth());
        appUserDto.setTitle(appUser.getTitle());
        appUserDto.setWebSite(appUser.getWebSite());
        appUserDto.setRcNumber(appUser.getRcNumber());
        appUserDto.setPicUrl(appUser.getPicUrl());
        appUserDto.setPhone(appUser.getPhone());
        appUserDto.setSchoolName(appUser.getSchoolName());
        appUserDto.setFirstName(appUser.getFirstName());
        appUserDto.setLastName(appUser.getLastName());
        appUserDto.setGender(appUser.getGender());
        appUserDto.setStreetNumber(appUser.getContact().getStreetNumber());
        appUserDto.setStreetName(appUser.getContact().getStreetName());
        appUserDto.setCity(appUser.getContact().getCity());
        appUserDto.setPostZipCode(appUser.getContact().getPostZipCode());
        appUserDto.setLandMark(appUser.getContact().getLandMark());
        appUserDto.setStateProvince(appUser.getContact().getStateProvince());
        appUserDto.setCountry(appUser.getContact().getCountry());
        appUserDto.setQualifications(appUser.getQualifications());
        appUserDto.setWorkExperiences(appUser.getWorkExperiences());
        appUserDto.setReferees(appUser.getReferees());

        return appUserDto;
    }
}
