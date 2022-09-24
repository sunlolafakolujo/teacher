package com.teacher.verificationtoken.service;

import com.teacher.appuser.model.AppUser;
import com.teacher.appuser.repository.AppUserRepository;
import com.teacher.verificationtoken.exception.VerificationTokeNotFoundException;
import com.teacher.verificationtoken.model.VerificationToken;
import com.teacher.verificationtoken.repository.VerificationTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.UUID;

@Service
@Transactional
public class VerificationTokenImpl implements VerificationTokenService{
    private VerificationTokenRepository verificationTokenRepository;
    private AppUserRepository appUserRepository;

    @Override
    public VerificationToken findVerificationByToken(String token) throws VerificationTokeNotFoundException {
        VerificationToken verificationToken=verificationTokenRepository.findByToken(token);
        return verificationToken;
    }

    @Override
    public VerificationToken findVerificationTokenByUser(AppUser appUser, String username) {
        AppUser user=appUserRepository.findUserByUsername(username);
        VerificationToken token=verificationTokenRepository.findByUser(user);
        return token;
    }

    @Override
    public void saveVerificationTokenForUser(String token, AppUser appUser) {
        VerificationToken verificationToken=new VerificationToken(token, appUser);
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public String validateVerificationToken(String token) throws VerificationTokeNotFoundException {
        VerificationToken verificationToken=verificationTokenRepository.findByToken(token);
        if (verificationToken==null){
            throw new VerificationTokeNotFoundException("Invalid token");
        }
        AppUser appUser=verificationToken.getAppUser();
        Calendar calendar=Calendar.getInstance();
        if ((verificationToken.getExpirationTime().getTime()-calendar.getTime().getTime()<=0)){
            verificationTokenRepository.delete(verificationToken);
            throw new VerificationTokeNotFoundException("Token expired");
        }
        appUser.setIsEnabled(true);
        appUserRepository.save(appUser);
        return "Valid";
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken=verificationTokenRepository.findByToken(oldToken);
        verificationToken.setToken(UUID.randomUUID().toString());
       return verificationTokenRepository.save(verificationToken);
    }
}
