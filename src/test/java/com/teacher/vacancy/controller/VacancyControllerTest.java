package com.teacher.vacancy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teacher.vacancy.model.Vacancy;
import com.teacher.vacancy.service.VacancyService;
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
class VacancyControllerTest {
    @Autowired
    private VacancyService vacancyService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    Vacancy vacancy;

    @BeforeEach
    void setUp() {
        vacancy=new Vacancy();
    }

    @Test
    void createVacancy() {
    }

    @Test
    void getVacancyById() {
    }

    @Test
    void getVacancyByJobId() {
    }

    @Test
    void getAllVacancies() {
    }

    @Test
    void updateVacancy() {
    }

    @Test
    void deleteVacancyById() {
    }

    @Test
    void deleteAllVacancies() {
    }
}