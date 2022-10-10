package com.teacher.vacancy.repository;

import com.teacher.appuser.model.AppUser;
import com.teacher.appuser.repository.AppUserRepository;
import com.teacher.vacancy.model.Vacancy;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
@Sql(scripts ={"classpath:db/insert.sql"})
class VacancyRepositoryTest {

    @Autowired
    private VacancyRepository vacancyRepository;

    Vacancy vacancy;

    @BeforeEach
    void setUp() {
        vacancy=new Vacancy();
    }

    @Test
    void testThatYouCanSaveVacancy(){
        vacancy.setBenefit("1. Pension "+"\n"+"2. Free Medical");
        vacancy.setCompanyName("City Pride School");
        vacancy.setJobId("JT-120");
        vacancy.setJobTitle("Head Teacher");
        vacancy.setExperienceEducation("ghjkiopp[[[pp[[iuyhyh");
        vacancy.setSkillRequirement("wduidwgduodhhpqpqwhqwo");
        vacancy.setKeyResponsibility("jkqjdgooiogqoigopiwgpig");
        vacancy.setJobLocation("Lagos\n");
        vacancy.setJobDetails(":\nEducation:\n"+vacancy.getExperienceEducation()+"\n\nSkills:\n"+vacancy.getSkillRequirement()
        +"\n\nKey Responsibility:\n"+vacancy.getKeyResponsibility()+"\n\nBenefits:\n"+vacancy.getBenefit());
        vacancy.setPublishedDate(LocalDate.parse("2022-10-10"));
        vacancy.setClosingDate(LocalDate.parse("2022-10-17"));

        assertDoesNotThrow(()->vacancyRepository.save(vacancy));

        log.info("Vacancy repo after saving:{}", vacancy);

    }

    @Test
    void testThatYouCanFindVacancyById(){

    }

    @Test
    void testThatYouCanFindVacancyByJobId(){

    }

    @Test
    void testThatYouCanFindVacancyByJobTitle(){

    }

    @Test
    void testThatYouCanFindAllVacancies(){

    }

    @Test
    void testThatYouCanUpdateVacancy(){

    }

    @Test
    @Rollback(value = false)
    void testThatYouCanDeleteVacancyById(){

    }

    @Test
    @Rollback(value = false)
    void testThatYouCanDeleteAllVacancy(){

    }
}