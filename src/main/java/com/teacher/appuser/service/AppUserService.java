package com.teacher.appuser.service;

import com.teacher.appuser.exception.AppUserNotFoundException;
import com.teacher.appuser.model.AppUser;
import com.teacher.verificationtoken.model.VerificationToken;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AppUserService {

    AppUser userRegistration(AppUser appUser);

    void saveVerificationTokenForUser(String token, AppUser appUser);

    String validateVerificationToken(String token);

    VerificationToken generateNewVerificationToken(String oldToken);

    void createPasswordTokenForUser(AppUser appUser, String token);

    AppUser findUserByUsername(String username) throws AppUserNotFoundException;

    String validatePasswordRestToken(String token);

    Optional<AppUser> getUserByPasswordResetToken(String token);

    void changePassword(AppUser appUser, String newPassword);

    AppUser findUserById(Long id) throws AppUserNotFoundException;

    AppUser findUserByEmail(String email) throws AppUserNotFoundException;

    AppUser findUserByPhone(String phone) throws AppUserNotFoundException;

    List<AppUser> findUserByFirstName(String firstName, Pageable pageable);

    List<AppUser> findUserByLastName(String lastName, Pageable pageable);

    List<AppUser> findBySchoolName(String schoolName);

    List<AppUser> findAllUsers(Pageable pageable);

    AppUser updateUser(AppUser appUser, Long id) throws AppUserNotFoundException;

    void deleteUserById(Long id) throws AppUserNotFoundException;

    void deleteAllUsers();
}
