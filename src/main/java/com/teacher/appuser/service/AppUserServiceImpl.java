package com.teacher.appuser.service;

import com.teacher.appuser.exception.AppUserNotFoundException;
import com.teacher.appuser.model.AppUser;
import com.teacher.appuser.repository.AppUserRepository;
import com.teacher.password.model.PasswordResetToken;
import com.teacher.password.repository.PasswordResetTokenRepository;
import com.teacher.staticdata.UserType;
import com.teacher.userrole.repository.UserRoleRepository;
import com.teacher.verificationtoken.model.VerificationToken;
import com.teacher.verificationtoken.repository.VerificationTokenRepository;
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
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public AppUser userRegistration(AppUser appUser) {

        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));

        return appUserRepository.save(appUser);
    }

    @Override
    public void saveVerificationTokenForUser(String token, AppUser appUser) {

        VerificationToken verificationToken=new VerificationToken(token, appUser);

        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public String validateVerificationToken(String token) {
        VerificationToken verificationToken=verificationTokenRepository.findByToken(token);

        if (verificationToken== null){
            return "invalid token";
        }

        AppUser appUser=verificationToken.getAppUser();
        Calendar calendar=Calendar.getInstance();

        if((verificationToken.getExpirationTime().getTime()
                - calendar.getTime().getTime())<=0){

            verificationTokenRepository.delete(verificationToken);

            return "token expired";
        }

        appUser.setEnabled(true);
        appUserRepository.save(appUser);

        return "valid token";
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken=verificationTokenRepository.findByToken(oldToken);

        verificationToken.setToken(UUID.randomUUID().toString());

        verificationTokenRepository.save(verificationToken);

        return verificationToken;
    }

    @Override
    public void createPasswordTokenForUser(AppUser appUser, String token) {

        PasswordResetToken passwordResetToken=new PasswordResetToken(token, appUser);

        passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public String validatePasswordResetToken(String token) {
        PasswordResetToken passwordResetToken=passwordResetTokenRepository.findByToken(token);

        if (passwordResetToken==null){
            return "invalid";
        }

//        AppUser appUser=passwordResetToken.getAppUser();
        Calendar calendar=Calendar.getInstance();

        if(passwordResetToken.getExpirationTime().getTime()- calendar.getTime().getTime()<=0){
            passwordResetTokenRepository.delete(passwordResetToken);
            return "expired";
        }

        return "valid ";
    }

    @Override
    public Optional<AppUser> getUserByPasswordResetToken(String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getAppUser());
    }

    @Override
    public void changePassword(AppUser appUser, String newPassword) {
        appUser.setPassword(passwordEncoder.encode(newPassword));

        appUserRepository.save(appUser);
    }

    @Override
    public boolean checkIfOldPassword(AppUser appUser, String oldPassword) {
        return passwordEncoder.matches(oldPassword, appUser.getPassword());
    }

    @Override
    public AppUser findUserById(Long id) throws AppUserNotFoundException {
        AppUser appUser=appUserRepository.findById(id)
                .orElseThrow(()->new AppUserNotFoundException("User ID "+id+" Not Found"));

        return appUser;
    }

    @Override
    public AppUser findUserByUsername(String username) throws AppUserNotFoundException {
        AppUser appUser= appUserRepository.findUserByUsername(username);

        if (appUser==null){
            throw new AppUserNotFoundException("User with username "+username+" Not Found");
        }

        return appUser;
    }

    @Override
    public AppUser findUserByEmail(String email) throws AppUserNotFoundException {
        AppUser appUser=appUserRepository.findUserByEmail(email);

        if (appUser==null){
            throw new AppUserNotFoundException("User with email "+email+" Not Found");
        }
        return appUser;
    }

    @Override
    public AppUser findUserByPhone(String phone) throws AppUserNotFoundException {
        AppUser  appUser=appUserRepository.findUserByPhone(phone);

        if (appUser==null){
            throw new AppUserNotFoundException("User with phone number "+phone+" Not Found");
        }
        return null;
    }

    @Override
    public List<AppUser> findUserByFirstName(String firstName, Pageable pageable) {

        pageable =PageRequest.of(0, 10);

        List<AppUser> appUserPage=appUserRepository.findUserByFirstName(firstName, pageable);

        return appUserPage;
    }

    @Override
    public List<AppUser> findUserByLastName(String lastName, Pageable pageable) {

        pageable =PageRequest.of(0, 10);

        List<AppUser> appUserPage=appUserRepository.findUserByFirstName(lastName, pageable);

        return appUserPage;
    }

    @Override
    public List<AppUser> findBySchoolName(String schoolName) {

        return appUserRepository.findBySchoolName(schoolName);
    }

    @Override
    public List<AppUser> findByUserType(UserType userType, Pageable pageable) {

        pageable=PageRequest.of(0, 10);

        List<AppUser> appUserPage=appUserRepository.findByUserType(userType, pageable);

        return appUserPage;
    }

    @Override
    public List<AppUser> findAllUsers(Pageable pageable) {
        pageable=PageRequest.of(0, 10);

        Page<AppUser> appUserPage=appUserRepository.findAll(pageable);

        return appUserPage.toList();
    }

    @Override
    public AppUser updateUser(AppUser appUser, Long id) throws AppUserNotFoundException {
        AppUser savedAppUser=appUserRepository.findById(id)
                .orElseThrow(()->new AppUserNotFoundException("User ID "+id+" Not Found"));

        if (Objects.nonNull(appUser.getFirstName()) && !"".equalsIgnoreCase(appUser.getFirstName())){
            savedAppUser.setFirstName(appUser.getFirstName());
        }if (Objects.nonNull(appUser.getLastName()) && !"".equalsIgnoreCase(appUser.getLastName())){
            savedAppUser.setLastName(appUser.getLastName());
        }if (Objects.nonNull(appUser.getPassword()) && !"".equalsIgnoreCase(appUser.getPassword())) {
            savedAppUser.setPassword(appUser.getPassword());
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
        }if (Objects.nonNull(appUser.getContact())) {
            savedAppUser.setContact(appUser.getContact());
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


}
