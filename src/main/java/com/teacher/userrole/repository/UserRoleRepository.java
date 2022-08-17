package com.teacher.userrole.repository;

import com.teacher.userrole.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long>,UserRoleRepositoryCustom {
}
