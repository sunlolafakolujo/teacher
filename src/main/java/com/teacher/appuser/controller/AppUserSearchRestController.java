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
    public ResponseEntity<AppUserDto> getUserByEmail(@PathVariable(value = "searchKey") String searchKey)
                                                                                    throws AppUserNotFoundException {
        AppUser appUser=appUserService.findByUsernameOrEmailOrMobileOrUserId(searchKey);
        AppUserDto appUserDto=convertAppUserToDto(appUser);
        return new ResponseEntity<>(appUserDto, HttpStatus.OK);
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
        appUserDto.setMobile(appUser.getMobile());
        appUserDto.setUserRoles(appUser.getUserRoles());

        return appUserDto;
    }

}
