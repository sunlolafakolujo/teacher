package com.teacher.userrole.service;

import com.teacher.appuser.exception.AppUserNotFoundException;
import com.teacher.appuser.model.AppUser;
import com.teacher.appuser.repository.AppUserRepository;
import com.teacher.userrole.exception.UserRoleNotFoundException;
import com.teacher.userrole.model.UserRole;
import com.teacher.userrole.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserRoleServiceImpl implements UserRoleService{

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public UserRole saveUserRole(UserRole userRole) throws UserRoleNotFoundException {
        Optional<UserRole> savedRole=userRoleRepository.findByRoleName(userRole.getRoleName());
        if (savedRole.isPresent()){
            throw new UserRoleNotFoundException("Role already exist");
        }
        return userRoleRepository.save(userRole);
    }

    @Override
    public UserRole findRoleById(Long id) throws UserRoleNotFoundException {
        UserRole userRole=userRoleRepository.findById(id)
                .orElseThrow(()->new UserRoleNotFoundException("Role Not Found"));
        return userRole;
    }

    @Override
    public UserRole findUserRoleByName(String roleName) throws UserRoleNotFoundException {
        UserRole userRole=userRoleRepository.findByRoleName(roleName)
                .orElseThrow(()->new UserRoleNotFoundException("Role "+roleName+" Not Found"));
        return userRole;
    }

    @Override
    public void addRoleToUser(String searchKey, String roleName) throws AppUserNotFoundException, UserRoleNotFoundException {
        AppUser appUser= appUserRepository.findByUsernameOrEmailOrMobile(searchKey,searchKey,searchKey,searchKey)
                .orElseThrow(()-> new AppUserNotFoundException("Username "+searchKey+" Not Found"));
        UserRole userRole=userRoleRepository.findByRoleName(roleName)
                .orElseThrow(()->new UserRoleNotFoundException("Role "+roleName+" Not Found"));
        appUser.getUserRoles().add(userRole);
        appUserRepository.save(appUser);
    }

    @Override
    public List<UserRole> findAllRoles(Pageable pageable) {
        pageable= PageRequest.of(0, 10);
        Page<UserRole> userRolePage=userRoleRepository.findAll(pageable);
        return userRolePage.toList();
    }

    @Override
    public UserRole updateRole(UserRole userRole, Long id) throws UserRoleNotFoundException {
        UserRole savedRole=userRoleRepository.findById(id)
                .orElseThrow(()->new UserRoleNotFoundException("Role Not Found"));
        if (Objects.nonNull(userRole.getRoleName()) && !"".equalsIgnoreCase(userRole.getRoleName())){
            savedRole.setRoleName(userRole.getRoleName());
        }
        return userRoleRepository.save(savedRole);
    }

    @Override
    public void deleteRoleById(Long id) throws UserRoleNotFoundException {
        userRoleRepository.deleteById(id);
        Optional<UserRole> optionalUserRole=userRoleRepository.findById(id);
        if (optionalUserRole.isPresent()){
            throw new UserRoleNotFoundException("Role Not Deleted");
        }
    }

    @Override
    public void deleteAllRoles() {
        userRoleRepository.deleteAll();
    }
}
