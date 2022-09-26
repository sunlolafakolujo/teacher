package com.teacher.userrole.service;

import com.teacher.staticdata.RoleName;
import com.teacher.userrole.exception.UserRoleNotFoundException;
import com.teacher.userrole.model.UserRole;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserRoleService {

    UserRole saveUserRole(UserRole userRole);
    UserRole findUserRoleByName(String roleName);
    void addRoleToUser(String username, String roleName);
    List<UserRole> findAllRoles(Pageable pageable);
    UserRole updateRole(UserRole userRole,Long id) throws UserRoleNotFoundException;
    void deleteRoleById(Long id) throws UserRoleNotFoundException;
    void deleteAllRoles();
}
