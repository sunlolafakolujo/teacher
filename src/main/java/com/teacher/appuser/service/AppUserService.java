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

    AppUser findUserByUsername(String username) throws AppUserNotFoundException;

    AppUser findUserByEmail(String email) throws AppUserNotFoundException;

    AppUser findUserByPhone(String phone) throws AppUserNotFoundException;

    List<AppUser> findUserByFirstName(String firstName, Pageable pageable) throws AppUserNotFoundException;

    List<AppUser> findUserByLastName(String lastName, Pageable pageable) throws AppUserNotFoundException;

    List<AppUser> findBySchoolName(String schoolName, Pageable pageable) throws AppUserNotFoundException;

    List<AppUser> findByUserType(UserType userType, Pageable pageable) throws AppUserNotFoundException;

    List<AppUser> findAllUsers(Pageable pageable);

    AppUser updateUser(AppUser appUser, Long id) throws AppUserNotFoundException;

    void deleteUserById(Long id) throws AppUserNotFoundException;

    void deleteAllUsers();
}
