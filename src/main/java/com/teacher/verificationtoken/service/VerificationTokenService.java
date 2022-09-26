package com.teacher.verificationtoken.service;

import com.teacher.appuser.model.AppUser;
import com.teacher.verificationtoken.exception.VerificationTokeNotFoundException;
import com.teacher.verificationtoken.model.VerificationToken;

import java.util.Optional;

public interface VerificationTokenService {
    VerificationToken findVerificationByToken(String token) throws VerificationTokeNotFoundException;
    Optional<AppUser> findVerificationTokenByUser(String token);
    void saveVerificationTokenForUser(String token, AppUser appUser);
    String validateVerificationToken(String token) throws VerificationTokeNotFoundException;
    VerificationToken generateNewVerificationToken(String oldToken);
}
