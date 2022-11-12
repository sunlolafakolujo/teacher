package com.teacher.userrole.service;

import com.teacher.appuser.exception.AppUserNotFoundException;
import com.teacher.staticdata.RoleName;
import com.teacher.userrole.exception.UserRoleNotFoundException;
import com.teacher.userrole.model.UserRole;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserRoleService {

    UserRole saveUserRole(UserRole userRole) throws UserRoleNotFoundException;
    UserRole findRoleById(Long id) throws UserRoleNotFoundException;
    UserRole findUserRoleByName(String roleName) throws UserRoleNotFoundException;
    void addRoleToUser(String username, String roleName) throws AppUserNotFoundException;
    List<UserRole> findAllRoles(Pageable pageable);
    UserRole updateRole(UserRole userRole,Long id) throws UserRoleNotFoundException;
    void deleteRoleById(Long id) throws UserRoleNotFoundException;
    void deleteAllRoles();
}
