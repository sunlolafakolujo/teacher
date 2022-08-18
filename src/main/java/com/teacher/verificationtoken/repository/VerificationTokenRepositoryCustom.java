package com.teacher.verificationtoken.repository;

import com.teacher.verificationtoken.model.VerificationToken;
import org.springframework.data.jpa.repository.Query;

public interface VerificationTokenRepositoryCustom {

    @Query("From VerificationToken v Where v.token=?1")
    VerificationToken findByToken(String token);
}
