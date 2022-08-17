package com.teacher.appuser.controller;

import com.teacher.appuser.model.AppUser;
import com.teacher.appuser.model.NewAppUser;
import com.teacher.appuser.service.AppUserService;
import com.teacher.event.RegistrationCompleteEvent;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/appUser")
@RequiredArgsConstructor
public class AppUserRestController {

    private final AppUserService appUserService;

    private final ModelMapper modelMapper;

    private ApplicationEventPublisher applicationEventPublisher;

    @PostMapping("/userRegistration")
    public ResponseEntity<NewAppUser> registerUser(@RequestBody @Valid NewAppUser newAppUser, final HttpServletRequest httpServletRequest){

        AppUser appUser=modelMapper.map(newAppUser, AppUser.class);

        AppUser post=appUserService.userRegistration(appUser);

        NewAppUser posted=modelMapper.map(post, NewAppUser.class);

        applicationEventPublisher.publishEvent(new RegistrationCompleteEvent(post, applicationUrl(httpServletRequest)));


        return new ResponseEntity<>(posted, HttpStatus.CREATED);
    }

    private String applicationUrl(HttpServletRequest httpServletRequest){
        return "http://" + httpServletRequest.getServerName() +
                ":" +
                httpServletRequest.getServerPort() +
                httpServletRequest.getContextPath();
    }
}
