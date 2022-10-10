package com.teacher.qualification.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teacher.contact.model.Contact;
import com.teacher.contact.service.ContactService;
import com.teacher.qualification.model.Qualification;
import com.teacher.qualification.service.QualificationService;
import com.teacher.staticdata.Status;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Sql(scripts = {"classpath:db/insert.sql"})
@AutoConfigureMockMvc
@Slf4j
class QualificationRestControllerTest {

    @Autowired
    private QualificationService qualificationService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    Qualification qualification;

    Contact contact;

    @BeforeEach
    void setUp() {
        qualification=new Qualification();
        contact=new Contact();
    }

    @Test
    void testThatWhenYouCallSaveQualificationMethod_thenQualificationIsSaved() throws Exception {

        qualification.setInstitutionName("College of Education");
        qualification.setSubject("Physics");
        qualification.setDegreeTitle("Bachelor of Education");
        qualification.setClassOfDegree("Upper Credit");
        qualification.setCurrentQualification(Status.YES);
        qualification.setStartDate(LocalDate.parse("1996-01-20"));
        qualification.setInstitutionAddress("Ijanikin, Lagos Badagry Expressway");

        this.mockMvc.perform(post("/api/qualification/saveQualification")
                .header(HttpHeaders.AUTHORIZATION, "Bearer ")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(qualification)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.school").value("College of Education"))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallGetQualificationByIdMethod_thenQualificationIsReturned() throws Exception {
        this.mockMvc.perform(get("/api/qualification/findQualificationById/2")
                .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.subject").value("Economics"))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallGetAllQualificationsMethod_thenQualificationsAreReturned() throws Exception {

        this.mockMvc.perform(get("/api/qualification/findAllQualifications")
                .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$[1].school").value("University of Lagos"))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallDeleteQualificationByIdMethod_thenQualificationIsDeleted() throws Exception {

        this.mockMvc.perform(delete("/api/qualification/deleteQualificationById/1")
                .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void testThatWhenYouCallDeleteAllQualificationsMethod_thenQualificationsAreDeleted() throws Exception {

        this.mockMvc.perform(delete("/api/qualification/deleteAllQualifications")
                .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}