package com.teacher.userrole.service;

import com.teacher.userrole.model.UserRole;

public interface UserRoleService {

    UserRole findUserByName(String roleName);
}
