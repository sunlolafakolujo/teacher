package com.teacher.applicationform.repository;

import com.teacher.applicationform.exception.ApplicationFormNotFoundException;
import com.teacher.applicationform.model.ApplicationForm;
import com.teacher.vacancy.exception.VacancyNotFoundException;
import com.teacher.vacancy.model.Vacancy;
import com.teacher.vacancy.repository.VacancyRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
@Transactional
@Sql(scripts = {"classpath:db/insert.sql"})
class ApplicationFormRepositoryTest {

    @Autowired
    private ApplicationFormRepository applicationFormRepository;

    @Autowired private VacancyRepository vacancyRepository;

    ApplicationForm applicationForm;
    Vacancy vacancy;

    @BeforeEach
    void setUp() {
        applicationForm=new ApplicationForm();
        vacancy=new Vacancy();
    }

//    @Test
//    void testThatYouCanSaveApplicationForm() throws VacancyNotFoundException {
//        Long id=1L;
//        vacancy=vacancyRepository.findById(id)
//                .orElseThrow(()->new VacancyNotFoundException("Vacancy Not Found"));
//        applicationForm.setEmail("zyx@yahoo.com");
//        applicationForm.setFirstName("Hakeem");
//        applicationForm.setLastName("Akala");
//        applicationForm.setLocation("Nigeria");
//        applicationForm.setPhone("08097535613");
//        applicationForm.setCoverLetterUrl("https://www.flexjobs.com/blog/post/customize-cover-letter-job-search/");
//        applicationForm.setResumeUrl("https://www.resumonk.com/resume-templates");
//        applicationForm.setVacancy(vacancy);
//        log.info("Application form repo before saving: {}", applicationForm);
//        assertDoesNotThrow(()->applicationFormRepository.save(applicationForm));
//        log.info("Application form repo after saving: {}", applicationForm);
//    }

    @Test
    void testThatYouCanFindApplicationFormById() throws ApplicationFormNotFoundException {
        Long id=1L;
        applicationForm=applicationFormRepository.findById(id)
                .orElseThrow(()->new ApplicationFormNotFoundException("Form Not Found"));
        log.info("Form: {} ", applicationForm);
    }

    @Test
    void testThatYouCanFinAllApplicationForms(){
        List<ApplicationForm> applicationForms=applicationFormRepository.findAll();
        applicationForms.stream().forEach(System.out::println);
    }

    @Rollback(value = false)
    @Test
    void testThatYouCanDeleteApplicationById() throws ApplicationFormNotFoundException {
        Long id=1L;
        applicationFormRepository.deleteById(id);
        Optional<ApplicationForm>optionalApplicationForm=applicationFormRepository.findById(id);
        if (optionalApplicationForm.isPresent())
            throw new ApplicationFormNotFoundException("Form "+id+" is Not Deleted");
    }

    @Rollback
    @Test
    void testThatYouCanDeleteAllApplications(){
        applicationFormRepository.deleteAll();
    }
}