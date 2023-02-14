package com.teacher.userrole.repository;

import com.teacher.staticdata.RoleName;
import com.teacher.userrole.model.UserRole;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRoleRepositoryCustom {

    @Query("From UserRole u Where u.roleName=?1")
    Optional<UserRole> findByRoleName(String roleName);
}
