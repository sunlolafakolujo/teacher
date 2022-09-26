package com.teacher.password.service;

import com.teacher.appuser.model.AppUser;
import com.teacher.password.exception.PasswordNotFoundException;
import com.teacher.password.model.PasswordToken;

import java.util.Optional;

public interface PasswordTokenService {
    Optional<AppUser> findUserByPasswordToken(String token);
    PasswordToken createPasswordResetTokenForUser(String token, AppUser appUser);
    String validatePasswordToken(String token) throws PasswordNotFoundException;
    void changeUserPassword(AppUser appUser,String newPassword);
    Boolean checkIfOldPasswordExist(AppUser appUser, String oldPassword);
}
