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

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

        workExperience.setStatus(Status.YES);
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
    void getWorkExperienceById() {
    }

    @Test
    void getAllWorkExperiences() {
    }

    @Test
    void deleteWorkExperienceById() {
    }

    @Test
    void deleteAllWorkExperiences() {
    }

    @Test
    void convertWorkExperienceToDto() {
    }
}