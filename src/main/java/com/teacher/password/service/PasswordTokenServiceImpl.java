package com.teacher.password.service;

import com.teacher.appuser.model.AppUser;
import com.teacher.appuser.repository.AppUserRepository;
import com.teacher.password.exception.PasswordNotFoundException;
import com.teacher.password.model.PasswordToken;
import com.teacher.password.repository.PasswordTokenRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Optional;

@Service
@Transactional
public class PasswordTokenServiceImpl implements PasswordTokenService{
    private PasswordTokenRepository passwordTokenRepository;
    private PasswordEncoder passwordEncoder;
    private AppUserRepository appUserRepository;

    @Override
    public Optional<AppUser> findUserByPasswordToken(String token) {
        return Optional.ofNullable(passwordTokenRepository.findByToken(token).getAppUser());
    }

    @Override
    public PasswordToken savePasswordTokenForUser(String token, AppUser appUser) {
        PasswordToken passwordToken=new PasswordToken(token, appUser);
        return passwordTokenRepository.save(passwordToken);
    }

    @Override
    public String validatePasswordToken(String token) throws PasswordNotFoundException {
        PasswordToken passwordToken=passwordTokenRepository.findByToken(token);
        if (passwordToken==null){
            throw new PasswordNotFoundException("Invalid token");
        }
        Calendar calendar=Calendar.getInstance();
        if ((passwordToken.getExpirationTime().getTime()-calendar.getTime().getTime()<=0)){
            passwordTokenRepository.delete(passwordToken);
            throw new PasswordNotFoundException("Password token expires");
        }
        return "Valid password token";
    }

    @Override
    public void changeUserPassword(AppUser appUser, String password) {
        AppUser appUserUsername=appUserRepository.findUserByUsername(appUser.getUsername());
        appUserUsername.setPassword(passwordEncoder.encode(password));
        appUserRepository.save(appUser);
    }

    @Override
    public Boolean checkIfOldPasswordExist(AppUser appUser, String oldPassword) {
        return passwordEncoder.matches(appUser.getPassword(), oldPassword);
    }
}
