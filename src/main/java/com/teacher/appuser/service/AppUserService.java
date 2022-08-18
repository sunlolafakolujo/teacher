package com.teacher.appuser.service;

import com.teacher.appuser.model.AppUser;
import com.teacher.verificationtoken.model.VerificationToken;

import java.util.Optional;

public interface AppUserService {

    AppUser userRegistration(AppUser appUser);

    void saveVerificationTokenForUser(String token, AppUser appUser);

    String validateVerificationToken(String token);

    VerificationToken generateNewVerificationToken(String oldToken);

    void createPasswordTokenForUser(AppUser appUser, String token);

    AppUser findUserByUsername(String username);

    String validatePasswordRestToken(String token);

    Optional<AppUser> getUserByPasswordResetToken(String token);

    void changePassword(AppUser appUser, String newPassword);
}
