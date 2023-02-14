package com.teacher.appuser.service;

import com.teacher.appuser.exception.AppUserNotFoundException;
import com.teacher.appuser.model.AppUser;
import com.teacher.staticdata.UserType;
import com.teacher.verificationtoken.model.VerificationToken;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AppUserService {

    AppUser userRegistration(AppUser appUser) throws AppUserNotFoundException;
    AppUser findUserById(Long id) throws AppUserNotFoundException;
    AppUser findByUsernameOrEmailOrMobileOrUserId(String searchKey) throws AppUserNotFoundException;
    List<AppUser> findByUserType(UserType userType, Pageable pageable) throws AppUserNotFoundException;
    List<AppUser> findAllUsers(Pageable pageable);
    Long countUsers();
    AppUser updateUser(AppUser appUser, Long id) throws AppUserNotFoundException;
    void deleteUserById(Long id) throws AppUserNotFoundException;
    void deleteAllUsers();
}
