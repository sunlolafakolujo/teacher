package com.teacher.userrole.repository;

import com.teacher.userrole.exception.UserRoleNotFoundException;
import com.teacher.userrole.model.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
class UserRoleRepositoryTest {
    @Autowired
    private UserRoleRepository userRoleRepository;

    UserRole userRole;
    @BeforeEach
    void setUp() {
        userRole=new UserRole();
    }

    @Test
    void testThatYouCanSaveRole(){
        userRole.setRoleName("GENERAL MANAGER");
        assertDoesNotThrow(()->userRoleRepository.save(userRole));
        log.info("UserRole repo after saving: {}", userRole);
    }

    @Test
    void testThatYouCanFindRoleByName() throws UserRoleNotFoundException {
        userRole=userRoleRepository.findByRoleName("PARENT");
        if (userRole==null){
            throw new UserRoleNotFoundException("Role Not Found");
        }
        log.info("User role: {}", userRole);
    }

    @Test
    void testThatYouCanFindAllRoles(){
        List<UserRole> userRolePage=userRoleRepository.findAll();
        log.info("Roles {}",userRolePage);
    }

    @Test
    void testThatYouCanUpdateRole(){
        Long id=4L;
        userRole=userRoleRepository.findById(id)
                .orElseThrow(()->new UsernameNotFoundException("Role Not Found"));
        userRole.setRoleName("ADMIN SUPER");
        assertDoesNotThrow(()->userRoleRepository.save(userRole));
        assertEquals("ADMIN SUPER", userRole.getRoleName());
        log.info("Updated role :{}", userRole.getRoleName());
    }

    @Test
    void testThatYouCanDeleteRoleById() throws UserRoleNotFoundException {
        Long id=4L;
        userRoleRepository.deleteById(id);
        Optional<UserRole> optionalUserRole=userRoleRepository.findById(id);
        if (optionalUserRole.isPresent()){
            throw new UserRoleNotFoundException("Role Not Deleted");
        }
    }

    @Test
    void testThatYouCanDeleteAllRoles(){
        userRoleRepository.deleteAll();
    }
}