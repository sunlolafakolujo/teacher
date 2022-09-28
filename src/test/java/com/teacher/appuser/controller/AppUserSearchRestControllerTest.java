package com.teacher.appuser.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teacher.appuser.model.AppUser;
import com.teacher.appuser.service.AppUserService;
import com.teacher.contact.model.Contact;
import com.teacher.contact.service.ContactService;
import com.teacher.qualification.model.Qualification;
import com.teacher.qualification.service.QualificationService;
import com.teacher.reference.model.Referee;
import com.teacher.reference.service.ReferenceService;
import com.teacher.workexperience.model.WorkExperience;
import com.teacher.workexperience.service.WorkExperienceService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
class AppUserSearchRestControllerTest {
    @Autowired
    private AppUserService appUserService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private ReferenceService referenceService;

    @Autowired
    private QualificationService qualificationService;

    @Autowired
    private WorkExperienceService workExperienceService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    AppUser appUser;
    Contact contact;
    Referee referee;
    Qualification qualification;
    WorkExperience workExperience;

    @BeforeEach
    void setUp() {
        appUser=new AppUser();
        contact=new Contact();
        referee=new Referee();
        workExperience=new WorkExperience();
    }

    @Test
    void schoolRegistration(){
    }
    @Test
    void teacherRegistration(){
    }
    @Test
    void parentRegistration(){
    }

    @Test
    void getUserById() {
    }

    @Test
    void getAllUsers() {
    }

    @Test
    void getUserByType() {
    }

    @Test
    void getUserByUsername() {
    }

    @Test
    void getUserByEmail() {
    }

    @Test
    void getUserByPhone() {
    }

    @Test
    void getUserByFirstName() {
    }

    @Test
    void getUserByLastName() {
    }

    @Test
    void getUserBySchoolName() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUserById() {
    }

    @Test
    void deleteAllUsersMethod() {
    }
}