package com.teacher.appuser.service;

import com.teacher.appuser.exception.AppUserNotFoundException;
import com.teacher.appuser.model.AppUser;
import com.teacher.appuser.repository.AppUserRepository;
import com.teacher.staticdata.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class AppUserServiceImpl implements AppUserService{

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AppUser userRegistration(AppUser appUser) throws AppUserNotFoundException {
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return appUserRepository.save(appUser);
    }

    @Override
    public AppUser findUserById(Long id) throws AppUserNotFoundException {
        AppUser appUser=appUserRepository.findById(id)
                .orElseThrow(()->new AppUserNotFoundException("User ID "+id+" Not Found"));
        return appUser;
    }

    @Override
    public AppUser findByUsernameOrEmailOrMobileOrUserId(String searchKey) throws AppUserNotFoundException {
        AppUser appUser=appUserRepository.findByUsernameOrEmailOrMobile(searchKey,searchKey,searchKey,searchKey)
                .orElseThrow(()->new AppUserNotFoundException("User "+searchKey+" Not Found"));

        return appUser;
    }

    @Override
    public List<AppUser> findByUserType(UserType userType, Pageable pageable) throws AppUserNotFoundException {
        pageable=PageRequest.of(0, 10);
        List<AppUser> appUserPage=appUserRepository.findByUserType(userType, pageable);
        if (appUserPage==null){
            throw new AppUserNotFoundException("User Type "+userType+" Not Found");
        }
        return appUserPage;
    }

    @Override
    public List<AppUser> findAllUsers(Pageable pageable) {
        pageable=PageRequest.of(0, 10);
        Page<AppUser> appUserPage=appUserRepository.findAll(pageable);
        return appUserPage.toList();
    }

    @Override
    public Long countUsers() {
        Long numberOfAppUser=appUserRepository.count();
        return numberOfAppUser;
    }

    @Override
    public AppUser updateUser(AppUser appUser, Long id) throws AppUserNotFoundException {
        AppUser savedAppUser=appUserRepository.findById(id)
                .orElseThrow(()->new AppUserNotFoundException("User ID "+id+" Not Found"));
        if (Objects.nonNull(appUser.getPassword()) && !"".equalsIgnoreCase(appUser.getPassword())) {
            savedAppUser.setPassword(appUser.getPassword());
        }if (Objects.nonNull(appUser.getContact())) {
            savedAppUser.setContact(appUser.getContact());
        }
        return appUserRepository.save(savedAppUser);
    }

    @Override
    public void deleteUserById(Long id) throws AppUserNotFoundException {
        appUserRepository.deleteById(id);
        Optional<AppUser> optionalAppUser=appUserRepository.findById(id);
        if (optionalAppUser.isPresent()){
            throw new AppUserNotFoundException("User ID "+id+" is Not Deleted");
        }
    }

    @Override
    public void deleteAllUsers() {
        appUserRepository.deleteAll();
    }

    private Boolean validateEmail(AppUser appUser){
        if (appUser.getUsername()!=null){
            return false;
        }
        return true;
    }
}
