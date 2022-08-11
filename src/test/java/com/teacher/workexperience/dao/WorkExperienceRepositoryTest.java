package com.teacher.workexperience.dao;

import com.teacher.staticdata.Status;
import com.teacher.workexperience.exception.WorkExperienceNotFoundException;
import com.teacher.workexperience.model.WorkExperience;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.teacher.staticdata.Status.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
class WorkExperienceRepositoryTest {

    @Autowired
    private WorkExperienceRepository workExperienceRepository;

    WorkExperience workExperience;

    @BeforeEach
    void setUp() {
        workExperience=new WorkExperience();
    }

    @Test
    void testThatYouCanSaveWorkExperience(){
        workExperience.setCompany("Mind Builders Group of Schools");
        workExperience.setPosition("Head Teacher");
        workExperience.setStatus(YES);
        workExperience.setStartDate(LocalDate.parse("2010-01-01"));

        log.info("Work experience repo before saving {}", workExperience);

        assertDoesNotThrow(()->workExperienceRepository.save(workExperience));

        log.info("Work experience repo after saving {}", workExperience);
    }

    @Test
    void testThatYoUCanFindWorkExperienceById() throws WorkExperienceNotFoundException {
        Long id=2L;
        workExperience=workExperienceRepository.findById(id)
                .orElseThrow(()-> new WorkExperienceNotFoundException("Work Experience "+id+" Not Found"));

        log.info("Work Experience ID 2: {}", workExperience);
    }

    @Test
    void testThatYouCanFindAllWorkExperience(){
        List<WorkExperience> workExperiences=workExperienceRepository.findAll();

        log.info("List of Work experiences {}", workExperiences);

        log.info("Number of work experiences {}", workExperiences.size());
    }

    @Test
    @Rollback(value = false)
    void testThatYouCanDeleteWorkExperienceById() throws WorkExperienceNotFoundException {

        Long id=1L;

        workExperienceRepository.deleteById(id);

        Optional<WorkExperience> optionalWorkExperience=workExperienceRepository.findById(id);

        if (optionalWorkExperience.isPresent()){
            throw new WorkExperienceNotFoundException("Work Experience is not deleted");
        }
    }

    @Test
    @Rollback(value = false)
    void testThatYouCanDeleteAllWorkExperience(){
        workExperienceRepository.deleteAll();
    }

}