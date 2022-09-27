package com.teacher.userrole.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teacher.userrole.exception.UserRoleNotFoundException;
import com.teacher.userrole.model.UserRole;
import com.teacher.userrole.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    void testThatWhenYouCallSaveRoleMethod_thenRoleIsSaved() throws Exception {
        userRole.setRoleName("GENERAL MANAGER");
        this.mockMvc.perform(post("/api/userRole/saveRole")
                .header(HttpHeaders.AUTHORIZATION, "Bearer ")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userRole)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.roleName", is("GENERAL MANAGER")))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallGetRoleByIdMethod_thenRoleIsReturned() throws Exception {
        this.mockMvc.perform(get("/api/userRole/findRoleById/{id}",2)
                .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.roleName", is("SCHOOL")))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallGetRoleByNameMethod_thenRoleIsReturned() throws Exception {
        this.mockMvc.perform(get("/api/userRole/findRoleByName/{roleName}","ADMIN")
                .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.roleName", is("ADMIN")))
                .andReturn();
    }

    @Test
    void testThatWhenYouCAllGetAllRolesMethod_thenRolesAreReturned() throws Exception {
        this.mockMvc.perform(get("/api/userRole/findAll")
                .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(4)))
                .andExpect(jsonPath("$[0].roleName", is("TEACHER")))
                .andReturn();
    }

    @Test
    void testThatWhenYouCAllUpdateRoleMethod_thenRoleIsUpdated() throws UserRoleNotFoundException, Exception {
        Long id=4L;
        userRole=userRoleService.findRoleById(id);
        userRole.setRoleName("SUPER ADMIN");
        userRoleService.updateRole(userRole, id);
        this.mockMvc.perform(put("/api/userRole/updateRole/{id}",4)
                .header(HttpHeaders.AUTHORIZATION, "Bearer ")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userRole)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userRole.getId()))
                .andExpect(jsonPath("$.roleName", is("SUPER ADMIN")))
                .andReturn();
    }

    @Test
    void testThatWhenYouCAllDeleteRoleMethod_thenRoleIsDeleted() throws Exception {
        this.mockMvc.perform(delete("/api/userRole/deleteRole/{id}",4)
                .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void testThatWhenYouCAllDeleteAllMethod_thenRolesAreDeleted() throws Exception {
        this.mockMvc.perform(delete("/api/userRole/deleteAllRoles")
                .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
    }
}