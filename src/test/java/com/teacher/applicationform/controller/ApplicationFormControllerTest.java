package com.teacher.applicationform.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teacher.applicationform.model.ApplicationForm;
import com.teacher.applicationform.service.ApplicationFormService;
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
@Transactional
@Sql(scripts = {"classpath:db/insert.sql"})
class ApplicationFormControllerTest {
    @Autowired
    private ApplicationFormService applicationFormService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    ApplicationForm applicationForm;

    @BeforeEach
    void setUp() {
        applicationForm=new ApplicationForm();
    }

    @Test
    void testThatWhenYouCallSaveApplicationMethod_thenApplicationIsSaved() throws Exception {

        applicationForm.setFirstName("Wale");
        applicationForm.setLastName("Okunola");
        applicationForm.setPhone("08078655677");
        applicationForm.setEmail("wokunola@yahoo.com");
        applicationForm.setLocation("Lagos, Nigeria");
        applicationForm.setResumeUrl("https://www.dayjob.com/accounts-payable-resume-1368/");
        applicationForm.setCoverLetterUrl("https://venngage.com/blog/cover-letter-template/");

        this.mockMvc.perform(post("/api/teacher/applicationForm/saveApplication")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer ")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(applicationForm)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.lastName", is("Okunola")))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallGetApplicationByIdMethod_thenApplicationIsReturned() throws Exception {
        this.mockMvc.perform(get("/api/teacher/applicationForm/findApplicationById/{id}",2)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.email", is("lakins@yahoo.com")))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallGetAllApplicationsMethod_thenApplicationsAreReturned() throws Exception {
        this.mockMvc.perform(get("/api/teacher/applicationForm/findAllApplications")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$[0].phone", is("09087545664")))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallDeleteApplicationByIdMethod_thenApplicationIsDeleted() throws Exception {
        this.mockMvc.perform(delete("/api/teacher/applicationForm/deleteApplicationById/{id}", 2)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void testThatWhenYouCallDeleteAllApplicationsMethod_thenApplicationsAreDeleted() throws Exception {
        this.mockMvc.perform(delete("/api/teacher/applicationForm/deleteAllApplications")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
    }
}