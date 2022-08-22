package com.teacher.workexperience.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teacher.staticdata.Status;
import com.teacher.workexperience.model.WorkExperience;
import com.teacher.workexperience.service.WorkExperienceService;
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

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
class WorkExperienceRestControllerTest {

    @Autowired
    private WorkExperienceService workExperienceService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private WorkExperience workExperience;

    @BeforeEach
    void setUp() {
        workExperience=new WorkExperience();
    }

    @Test
    void testThatWhenYouCannSaveWorkExperience_thenWorkExperienceIsSaved() throws Exception {

        workExperience.setCurrentWork(Status.YES);
        workExperience.setPosition("Teacher");
        workExperience.setCompany("Hill Crest School");
        workExperience.setStartDate(LocalDate.parse("2020-01-23"));

        this.mockMvc.perform(post("/api/workExperience/saveWorkExperience")
                .header(AUTHORIZATION, "Bearer ")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(workExperience)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.company").value("Hill Crest School"))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallGetWorkExperienceById_thenWorkExperienceIsReturned() throws Exception {

        this.mockMvc.perform(get("/api/workExperience/findWorkExperienceById/1")
                .header(AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.company").value("Babington Macaulay Junior Seminary"))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallGetAllWorkExperiences_thenWorkExperiencesAreReturned() throws Exception {

        this.mockMvc.perform(get("/api/workExperience/findAllWorkExperiences")
                .header(AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$[1].company").value("Dansol High School"))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallDeleteWorkExperienceById_thenWorkExperienceIsDeleted() throws Exception {

        this.mockMvc.perform(delete("/api/workExperience/deleteWorkExperienceById/1")
                .header(AUTHORIZATION, "Bearer "))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void testThatWhenYouCallDeleteAllWorkExperiences_thenWorkExperiencesAreDeleted() throws Exception {

        this.mockMvc.perform(delete("/api/workExperience/deleteAllWorkExperiences")
                .header(AUTHORIZATION, "Bearer "))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}