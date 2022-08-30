package com.teacher.password.repository;

import com.teacher.password.model.PasswordResetToken;
import org.springframework.data.jpa.repository.Query;

public interface PasswordResetTokenRepositoryCustom {

    @Query("From PasswordResetToken p Where p.token=?1")
    PasswordResetToken findByToken(String token);
}
