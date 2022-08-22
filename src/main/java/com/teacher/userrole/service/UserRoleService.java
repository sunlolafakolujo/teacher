package com.teacher.userrole.service;

import com.teacher.staticdata.RoleName;
import com.teacher.userrole.model.UserRole;

public interface UserRoleService {

    UserRole saveUserRole(UserRole userRole);
    UserRole findUserRoleByName(String roleName);
}
