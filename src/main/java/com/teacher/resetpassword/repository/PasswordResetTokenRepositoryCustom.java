package com.teacher.resetpassword.repository;

import com.teacher.resetpassword.model.PasswordResetToken;
import org.springframework.data.jpa.repository.Query;

public interface PasswordResetTokenRepositoryCustom {

    @Query("From PasswordResetToken p Where p.token=?1")
    PasswordResetToken findByToken(String token);
}
