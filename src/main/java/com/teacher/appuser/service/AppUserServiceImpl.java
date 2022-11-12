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
    public AppUser userRegistration(AppUser appUser) {
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
    public AppUser findUserByUsername(String username) throws AppUserNotFoundException {
        AppUser appUser=appUserRepository.findUserByUsername(username)
                .orElseThrow(()->new AppUserNotFoundException("User "+username+" Not Found"));

        return appUser;
    }

    @Override
    public AppUser findUserByEmail(String email) throws AppUserNotFoundException {
        AppUser appUser=appUserRepository.findUserByEmail(email)
                .orElseThrow(()-> new AppUserNotFoundException("User email "+email+" Not Found"));

        return appUser;
    }

    @Override
    public AppUser findUserByPhone(String phone) throws AppUserNotFoundException {
        AppUser  appUser=appUserRepository.findUserByPhone(phone)
                .orElseThrow(()-> new AppUserNotFoundException("User phone "+phone+" Not Found"));

        return appUser;
    }

    @Override
    public List<AppUser> findUserByFirstName(String firstName, Pageable pageable) throws AppUserNotFoundException {
        pageable =PageRequest.of(0, 10);
        List<AppUser> appUserPage=appUserRepository.findUserByFirstName(firstName, pageable);
        if (appUserPage==null){
            throw new AppUserNotFoundException("Firstname "+firstName+" Not Found");
        }
        return appUserPage;
    }

    @Override
    public List<AppUser> findUserByLastName(String lastName, Pageable pageable) throws AppUserNotFoundException {
        pageable =PageRequest.of(0, 10);
        List<AppUser> appUserPage=appUserRepository.findUserByLastName(lastName, pageable);
        if (appUserPage==null){
            throw new AppUserNotFoundException("Lastname "+lastName+" Not Found");
        }
        return appUserPage;
    }

    @Override
    public List<AppUser> findBySchoolName(String schoolName,Pageable pageable) throws AppUserNotFoundException {
        pageable=PageRequest.of(0, 10);
        List<AppUser> appUsers=appUserRepository.findBySchoolName(schoolName,pageable);
        if (appUsers==null){
            throw new AppUserNotFoundException("School name "+schoolName+" Not Found");
        }
        return appUsers;
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
        if (Objects.nonNull(appUser.getFirstName()) && !"".equalsIgnoreCase(appUser.getFirstName())){
            savedAppUser.setFirstName(appUser.getFirstName());
        }if (Objects.nonNull(appUser.getLastName()) && !"".equalsIgnoreCase(appUser.getLastName())){
            savedAppUser.setLastName(appUser.getLastName());
        }if (Objects.nonNull(appUser.getEmail()) && !"".equalsIgnoreCase(appUser.getEmail())) {
            savedAppUser.setEmail(appUser.getEmail());
        }if (Objects.nonNull(appUser.getPicUrl()) && !"".equalsIgnoreCase(appUser.getPicUrl())) {
            savedAppUser.setPicUrl(appUser.getPicUrl());
        }if (Objects.nonNull(appUser.getSchoolName()) && !"".equalsIgnoreCase(appUser.getSchoolName())) {
            savedAppUser.setSchoolName(appUser.getSchoolName());
        }if (Objects.nonNull(appUser.getWebSite()) && !"".equalsIgnoreCase(appUser.getWebSite())) {
            savedAppUser.setWebSite(appUser.getWebSite());
        }if (Objects.nonNull(appUser.getMeansOfIdentification())) {
            savedAppUser.setMeansOfIdentification(appUser.getMeansOfIdentification());
        }if (Objects.nonNull(appUser.getMeansOfIdentificationIssueDate())) {
            savedAppUser.setMeansOfIdentificationIssueDate(appUser.getMeansOfIdentificationExpiryDate());
        }if (Objects.nonNull(appUser.getMeansOfIdentificationExpiryDate())) {
            savedAppUser.setMeansOfIdentificationExpiryDate(appUser.getMeansOfIdentificationExpiryDate());
        }if (Objects.nonNull(appUser.getPassword()) && !"".equalsIgnoreCase(appUser.getPassword())) {
            savedAppUser.setPassword(appUser.getPassword());
        }if (Objects.nonNull(appUser.getContacts())) {
            savedAppUser.setContacts(appUser.getContacts());
        }if (Objects.nonNull(appUser.getReferees())) {
            savedAppUser.setReferees(appUser.getReferees());
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
