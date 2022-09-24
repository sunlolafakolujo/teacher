package com.teacher.appuser.controller;

import com.teacher.appuser.exception.AppUserNotFoundException;
import com.teacher.appuser.model.*;
import com.teacher.appuser.service.AppUserService;
import com.teacher.event.RegistrationCompleteEvent;
import com.teacher.password.model.PasswordModel;
import com.teacher.staticdata.UserType;
import com.teacher.userrole.model.UserRole;
import com.teacher.userrole.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

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
    public ResponseEntity<SchoolDto> schoolRegister(@RequestBody @Valid SchoolDto schoolDto,
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

        return new ResponseEntity<>(posted, HttpStatus.CREATED);
    }

    private String applicationUrl(HttpServletRequest httpServletRequest){
        return "http://"+ httpServletRequest.getServerName() +
                ":" +
                httpServletRequest.getServerPort() +
                httpServletRequest.getContextPath();
    }
}
