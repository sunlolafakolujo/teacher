package com.teacher.appuser.service;

import com.teacher.appuser.model.AppUser;
import com.teacher.appuser.repository.AppUserRepository;
import com.teacher.resetpassword.model.PasswordResetToken;
import com.teacher.resetpassword.repository.PasswordResetTokenRepository;
import com.teacher.staticdata.RoleName;
import com.teacher.userrole.model.UserRole;
import com.teacher.userrole.repository.UserRoleRepository;
import com.teacher.verificationtoken.model.VerificationToken;
import com.teacher.verificationtoken.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AppUserServiceImpl implements AppUserService{

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

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

    @Override
    public String validateVerificationToken(String token) {
        VerificationToken verificationToken=verificationTokenRepository.findByToken(token);

        if (verificationToken==null){
            return "invalid";
        }

        AppUser appUser=verificationToken.getAppUser();
        Calendar calendar=Calendar.getInstance();

        if(verificationToken.getExpirationTime().getTime()- calendar.getTime().getTime()<=0){
            verificationTokenRepository.delete(verificationToken);
            return "expired";
        }

        appUser.setEnabled(true);
        appUserRepository.save(appUser);

        return "valid ";
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken=verificationTokenRepository.findByToken(oldToken);

        verificationToken.setToken(UUID.randomUUID().toString());

        verificationTokenRepository.save(verificationToken);

        return verificationToken;
    }

    @Override
    public void createPasswordTokenForUser(AppUser appUser, String token) {

        PasswordResetToken passwordResetToken=new PasswordResetToken(token, appUser);

        passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public AppUser findUserByUsername(String username) {
        AppUser appUser= appUserRepository.findUserByUsername(username);

        return appUser;
    }

    @Override
    public String validatePasswordRestToken(String token) {
        PasswordResetToken passwordResetToken=passwordResetTokenRepository.findByToken(token);

        if (passwordResetToken==null){
            return "invalid";
        }

        AppUser appUser=passwordResetToken.getAppUser();
        Calendar calendar=Calendar.getInstance();

        if(passwordResetToken.getExpirationTime().getTime()- calendar.getTime().getTime()<=0){
            passwordResetTokenRepository.delete(passwordResetToken);
            return "expired";
        }

        return "valid ";
    }

    @Override
    public Optional<AppUser> getUserByPasswordResetToken(String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getAppUser());
    }

    @Override
    public void changePassword(AppUser appUser, String newPassword) {
        appUser.setPassword(passwordEncoder.encode(newPassword) );

        appUserRepository.save(appUser);
    }

}
