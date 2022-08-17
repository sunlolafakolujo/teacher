package com.teacher.appuser.service;

import com.teacher.appuser.model.AppUser;

public interface AppUserService {

    AppUser userRegistration(AppUser appUser);

    void saveVerificationTokenForUser(String token, AppUser appUser);
}
