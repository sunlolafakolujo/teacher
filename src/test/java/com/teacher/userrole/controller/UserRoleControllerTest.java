package com.teacher.userrole.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teacher.userrole.model.UserRole;
import com.teacher.userrole.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
@Transactional
class UserRoleControllerTest {
    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    UserRole userRole;

    @BeforeEach
    void setUp() {
        userRole=new UserRole();
    }

    @Test
    void saveRole() {
    }

    @Test
    void getRoleById() {
    }

    @Test
    void getRoleByName() {
    }

    @Test
    void getAllRoles() {
    }

    @Test
    void updateRole() {
    }

    @Test
    void deleteRole() {
    }

    @Test
    void deleteAll() {
    }
}