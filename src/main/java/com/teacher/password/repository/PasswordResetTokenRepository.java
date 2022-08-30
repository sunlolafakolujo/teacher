package com.teacher.password.repository;

import com.teacher.password.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long>, PasswordResetTokenRepositoryCustom {

}
