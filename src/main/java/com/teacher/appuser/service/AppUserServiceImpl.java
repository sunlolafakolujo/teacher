package com.teacher.appuser.service;

import com.teacher.appuser.model.AppUser;
import com.teacher.appuser.repository.AppUserRepository;
import com.teacher.verificationtoken.model.VerificationToken;
import com.teacher.verificationtoken.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AppUserServiceImpl implements AppUserService{

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
}
