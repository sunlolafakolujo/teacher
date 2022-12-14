package com.teacher.appuser.repository;

import com.teacher.appuser.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AppUserRepository extends JpaRepository<AppUser, Long>, AppUserRepositoryCustom {
}
