package com.teacher.userrole.service;

import com.teacher.appuser.model.AppUser;
import com.teacher.appuser.repository.AppUserRepository;
import com.teacher.staticdata.RoleName;
import com.teacher.userrole.model.UserRole;
import com.teacher.userrole.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService{

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public UserRole saveUserRole(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

    @Override
    public UserRole findUserRoleByName(String roleName) {
        UserRole userRole=userRoleRepository.findUserRoleByName(roleName);
        return userRole;
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser appUser= appUserRepository.findUserByUsername(username);
        UserRole userRole=userRoleRepository.findUserRoleByName(roleName);
        appUser.getUserRoles().add(userRole);
    }
}
