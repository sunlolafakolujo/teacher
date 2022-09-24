package com.teacher.password.repository;

import com.teacher.password.model.PasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordTokenRepository extends JpaRepository<PasswordToken, Long>, PasswordTokenRepositoryCustom {

}
