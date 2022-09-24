package com.teacher.verificationtoken.service;

import com.teacher.appuser.model.AppUser;
import com.teacher.verificationtoken.exception.VerificationTokeNotFoundException;
import com.teacher.verificationtoken.model.VerificationToken;

public interface VerificationTokenService {
    VerificationToken findVerificationByToken(String token) throws VerificationTokeNotFoundException;
    VerificationToken findVerificationTokenByUser(AppUser appUser, String username);
    void saveVerificationTokenForUser(String token, AppUser appUser);
    String validateVerificationToken(String token) throws VerificationTokeNotFoundException;
    VerificationToken generateNewVerificationToken(String oldToken);
}
