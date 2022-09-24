package com.teacher.password.repository;

import com.teacher.password.model.PasswordToken;
import org.springframework.data.jpa.repository.Query;

public interface PasswordTokenRepositoryCustom {

    @Query("From PasswordResetToken p Where p.token=?1")
    PasswordToken findByToken(String token);
}
