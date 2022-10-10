package com.teacher.vacancy.service;

import com.teacher.vacancy.model.Vacancy;
import com.teacher.vacancy.repository.VacancyRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
class VacancyServiceImplTest {
    @Mock
    private VacancyRepository vacancyRepository;

    @InjectMocks
    private VacancyService vacancyService=new VacancyServiceImpl();

    Vacancy vacancy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        vacancy=new Vacancy();
    }

    @Test
    void testThatYouCanMockSaveVacancyMethod() {
    }

    @Test
    void findVacancyById() {
    }

    @Test
    void findVacancyByJobId() {
    }

    @Test
    void findVacancyByJobTitle() {
    }

    @Test
    void findAllVacancies() {
    }

    @Test
    void countVacancy() {
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