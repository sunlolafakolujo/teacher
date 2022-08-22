package com.teacher.userrole.service;

import com.teacher.staticdata.RoleName;
import com.teacher.userrole.model.UserRole;
import com.teacher.userrole.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService{

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public UserRole saveUserRole(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

    @Override
    public UserRole findUserRoleByName(String roleName) {
        UserRole userRole=userRoleRepository.findUserRoleByName(roleName);
        return userRole;
    }
}
