package com.teacher.appuser.service;

import com.teacher.appuser.model.AppUser;
import com.teacher.appuser.repository.AppUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
class AppUserServiceImplTest {
    @Mock
    private AppUserRepository appUserRepository;

    @InjectMocks
    private AppUserService appUserService=new AppUserServiceImpl();

    AppUser appUser;

    @BeforeEach
    void setUp() {
        appUser=new AppUser();
    }

    @Test
    void userRegistration() {
    }

    @Test
    void findUserById() {
    }

    @Test
    void findUserByUsername() {
    }

    @Test
    void findUserByEmail() {
    }

    @Test
    void findUserByPhone() {
    }

    @Test
    void findUserByFirstName() {
    }

    @Test
    void findUserByLastName() {
    }

    @Test
    void findBySchoolName() {
    }

    @Test
    void findByUserType() {
    }

    @Test
    void findAllUsers() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUserById() {
    }

    @Test
    void deleteAllUsers() {
    }
}