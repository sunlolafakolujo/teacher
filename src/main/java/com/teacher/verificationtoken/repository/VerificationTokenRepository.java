package com.teacher.verificationtoken.repository;

import com.teacher.verificationtoken.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long>,VerificationTokenRepositoryCustom {
}
