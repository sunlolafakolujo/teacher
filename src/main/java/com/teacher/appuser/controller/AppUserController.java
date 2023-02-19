package com.teacher.appuser.controller;

import com.teacher.appuser.exception.AppUserNotFoundException;
import com.teacher.appuser.model.AppUser;
import com.teacher.appuser.model.AppUserDto;
import com.teacher.appuser.model.NewAppUser;
import com.teacher.appuser.model.UpdateAppUser;
import com.teacher.appuser.service.AppUserService;
import com.teacher.event.RegistrationCompleteEvent;
import com.teacher.staticdata.UserType;
import com.teacher.userrole.service.UserRoleService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/teacher/appUser")
@AllArgsConstructor
public class AppUserController {
    private final AppUserService appUserService;
    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher applicationEventPublisher;

    @PostMapping("/addUser")
    public ResponseEntity<NewAppUser> addUser(@RequestBody NewAppUser newAppUser, HttpServletRequest request)
                                                                            throws AppUserNotFoundException {
        AppUser appUser=modelMapper.map(newAppUser,AppUser.class);
        AppUser post=appUserService.userRegistration(appUser);
        NewAppUser posted=modelMapper.map(post,NewAppUser.class);
        applicationEventPublisher.publishEvent(new RegistrationCompleteEvent(post,applicationUrl(request)));
        return new ResponseEntity<>(posted,HttpStatus.CREATED);
    }

    @GetMapping("/findUserById")
    public ResponseEntity<AppUserDto> getUserById(@RequestParam("id") Long id) throws AppUserNotFoundException {
        AppUser appUser=appUserService.findUserById(id);
        AppUserDto appUserDto=convertAppUserToDto(appUser);
        return new ResponseEntity<>(appUserDto, HttpStatus.OK);
    }

    @GetMapping("/findAllUsersOrByType")
    public ResponseEntity<List<AppUserDto>> getAllUsers(@RequestParam(defaultValue = "0")Integer pageNumber,
                                                        @RequestParam("userType")UserType userType){
        return new ResponseEntity<>(appUserService.findAllUsers(userType, pageNumber)
                .stream()
                .map(this::convertAppUserToDto)
                .collect(Collectors.toList()),HttpStatus.OK);
    }

    @GetMapping("/findByUsernameOrEmailOrMobileOUserId")
    public ResponseEntity<AppUserDto> getUserByEmail(@RequestParam(defaultValue = "") String searchKey)
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

    @PutMapping("/updateUser")
    public ResponseEntity<UpdateAppUser> edit(@RequestBody UpdateAppUser updateAppUser,
                                              @RequestParam("id") Long id) throws AppUserNotFoundException {
        AppUser appUser=modelMapper.map(updateAppUser, AppUser.class);
        AppUser post=appUserService.updateUser(appUser, id);
        UpdateAppUser posted=modelMapper.map(post, UpdateAppUser.class);
        return new ResponseEntity<>(posted, HttpStatus.OK);
    }

    @DeleteMapping("/deleteUserById")
    public ResponseEntity<?> deleteUserById(@RequestParam("id") Long id) throws AppUserNotFoundException {
        appUserService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteAllUsers")
    public ResponseEntity<?> deleteAllUsers(){
        appUserService.deleteAllUsers();
        return ResponseEntity.noContent().build();
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
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
