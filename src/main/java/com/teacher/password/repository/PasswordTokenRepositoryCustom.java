package com.teacher.password.repository;

import com.teacher.appuser.model.AppUser;
import com.teacher.password.model.PasswordToken;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PasswordTokenRepositoryCustom {

    @Query("From PasswordToken p Where p.token=?1")
    PasswordToken findByToken(String token);
    @Query("From PasswordToken p Where p.appUser=?1")
    AppUser findByAppUser(AppUser appUser);

}
