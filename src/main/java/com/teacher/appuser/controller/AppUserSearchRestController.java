package com.teacher.appuser.controller;

import com.teacher.appuser.exception.AppUserNotFoundException;
import com.teacher.appuser.model.AppUser;
import com.teacher.appuser.model.AppUserDto;
import com.teacher.appuser.service.AppUserService;
import com.teacher.staticdata.UserType;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/teacher/appUser")
@AllArgsConstructor
public class AppUserSearchRestController {
    private final AppUserService appUserService;

    @GetMapping("/findUserById/{id}")
    public ResponseEntity<AppUserDto> getUserById(@PathVariable(value = "id") Long id) throws AppUserNotFoundException {
        AppUser appUser=appUserService.findUserById(id);
        AppUserDto appUserDto=convertAppUserToDto(appUser);
        return new ResponseEntity<>(appUserDto, HttpStatus.OK);
    }

    @GetMapping("/findAllUsers")
    @PreAuthorize("hasRole('SCHOOL')")
    public ResponseEntity<List<AppUserDto>> getAllUsers(){
        Pageable pageable= PageRequest.of(0, 10);
        return new ResponseEntity<>(appUserService.findAllUsers(pageable)
                .stream()
                .map(this::convertAppUserToDto)
                .collect(Collectors.toList()),HttpStatus.OK);
    }

    @GetMapping("/findUserByType/{userType}")
    public ResponseEntity<List<AppUserDto>> getUserByType(@PathVariable(value = "userType")UserType userType) throws AppUserNotFoundException {
        Pageable pageable= PageRequest.of(0, 10);
        return new ResponseEntity<>(appUserService.findByUserType(userType,pageable)
                .stream()
                .map(this::convertAppUserToDto)
                .collect(Collectors.toList()),HttpStatus.OK);
    }

    @GetMapping("/findUserByEmail/{email}")
    public ResponseEntity<AppUserDto> getUserByEmail(@PathVariable(value = "email") String email)
            throws AppUserNotFoundException {
        AppUser appUser=appUserService.findUserByEmail(email);
        AppUserDto appUserDto=convertAppUserToDto(appUser);
        return new ResponseEntity<>(appUserDto, HttpStatus.OK);
    }

    @GetMapping("/findUserByPhone/{phone}")
    public ResponseEntity<AppUserDto> getUserByPhone(@PathVariable(value = "phone") String phone)
            throws AppUserNotFoundException {
        AppUser appUser=appUserService.findUserByPhone(phone);
        AppUserDto appUserDto=convertAppUserToDto(appUser);
        return new ResponseEntity<>(appUserDto, HttpStatus.OK);
    }

    @GetMapping("/findUserByFirstName/{firstName}")
    public ResponseEntity<List<AppUserDto>> getUserByFirstName(@PathVariable(value = "firstName") String firstName)
                                                                                throws AppUserNotFoundException {
        Pageable pageable=PageRequest.of(0, 10);
        return new ResponseEntity<>(appUserService.findUserByFirstName(firstName, pageable)
                .stream()
                .map(this::convertAppUserToDto)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/findUserByLastName/{lastName}")
    public ResponseEntity<List<AppUserDto>> getUserByLastName(@PathVariable(value = "lastName") String lastName)
                                                                                throws AppUserNotFoundException {
        Pageable pageable=PageRequest.of(0, 10);
        return new ResponseEntity<>(appUserService.findUserByLastName(lastName, pageable)
                .stream()
                .map(this::convertAppUserToDto)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/findUserBySchoolName/{schoolName}")
    public ResponseEntity<List<AppUserDto>> getUserBySchoolName(@PathVariable(value = "schoolName") String schoolName)
                                                                                    throws AppUserNotFoundException {
        Pageable pageable=PageRequest.of(0, 10);
        return new ResponseEntity<>(appUserService.findBySchoolName(schoolName,pageable)
                .stream()
                .map(this::convertAppUserToDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/countUsers")
    public ResponseEntity<Long> countNumberOfUsers(){
        Long numberOfUsers=appUserService.countUsers();
        return new ResponseEntity<>(numberOfUsers, HttpStatus.OK);
    }

    private AppUserDto convertAppUserToDto(AppUser appUser){

        AppUserDto appUserDto=new AppUserDto();

        appUserDto.setUserId(appUser.getUserId());
        appUserDto.setUserType(appUser.getUserType());
        appUserDto.setUsername(appUser.getUsername());
        appUserDto.setEmail(appUser.getEmail());
        appUserDto.setAge(appUser.getAge());
        appUserDto.setMeansOfIdentification(appUser.getMeansOfIdentification());
        appUserDto.setMeansOfIdentificationRefNumber(appUser.getMeansOfIdentificationRefNumber());
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
        appUserDto.setContacts(appUser.getContacts());
        appUserDto.setQualifications(appUser.getQualifications());
        appUserDto.setWorkExperiences(appUser.getWorkExperiences());
        appUserDto.setReferees(appUser.getReferees());
        appUserDto.setUserRoles(appUserDto.getUserRoles());

        return appUserDto;
    }

}
