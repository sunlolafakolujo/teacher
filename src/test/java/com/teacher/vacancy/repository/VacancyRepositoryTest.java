package com.teacher.vacancy.repository;

import com.teacher.appuser.model.AppUser;
import com.teacher.appuser.repository.AppUserRepository;
import com.teacher.staticdata.JobType;
import com.teacher.vacancy.exception.VacancyNotFoundException;
import com.teacher.vacancy.model.Vacancy;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
        vacancy.setPublishedDate(LocalDate.parse("2022-10-10"));
        vacancy.setClosingDate(LocalDate.parse("2022-10-17"));
        vacancy.setJobType(JobType.FULL_TIME);
        vacancy.setJobSchedule("Monday-Friday, 07:00am-16:00pm");
//        vacancy.setJobId("JT-120");
        vacancy.setJobTitle("Head Teacher");
        vacancy.setQualification("ghjkiopp[[[pp[[iuyhyh");
        vacancy.setSkillRequirement("wduidwgduodhhpqpqwhqwo");
        vacancy.setKeyResponsibility("jkqjdgooiogqoigopiwgpig");
        vacancy.setJobLocation("Lagos\n");
        vacancy.setJobDetail(":\nCompany:"+vacancy.getCompanyName()+
//                            "\n\nJobId:"+vacancy.getJobId()+
                            "\n\nPublished Date:"+vacancy.getPublishedDate()+
                            "\n\nClosing Date:"+vacancy.getClosingDate()+
                            "\n\nJob Title:"+vacancy.getJobTitle()+
                            "\n\nJob Type:"+vacancy.getJobType()+
                            "\n\nJob Location:"+vacancy.getJobLocation()+
                            "\n\nJob Schedule:"+vacancy.getJobSchedule()+
                            "\n\nQualification:\n"+vacancy.getQualification()+
                            "\n\nSkills:\n"+vacancy.getSkillRequirement() +
                            "\n\nKey Responsibility:\n"+vacancy.getKeyResponsibility()+
                            "\n\nBenefits:\n"+vacancy.getBenefit());


        assertDoesNotThrow(()->vacancyRepository.save(vacancy));

        log.info("Vacancy repo after saving {}", vacancy.getJobDetail());
    }

    @Test
    void testThatYouCanFindVacancyById() throws VacancyNotFoundException {
        Long id=1L;
        vacancy=vacancyRepository.findById(id)
                .orElseThrow(()->new VacancyNotFoundException("Vacancy Not Found"));
        log.info("Vacancy: {}", vacancy);
    }

//    @Test
//    void testThatYouCanFindVacancyByJobId() throws VacancyNotFoundException {
//        String jobId="JID102";
//        vacancy=vacancyRepository.findByJobId(jobId);
//        if (vacancy==null){
//            throw new VacancyNotFoundException("Vacancy Not Found");
//        }
//        log.info("Vacancy: {}", vacancy);
//    }

    @Test
    void testThatYouCanFindVacancyByJobTitle(){
        String jobTitle="Physic Teacher";
        Pageable pageable= PageRequest.of(0, 10);
        List<Vacancy> vacancies=vacancyRepository.findByJobTitle(jobTitle,pageable);
        vacancies.forEach(System.out::println);
    }

    @Test
    void testThatYouCanFindAllVacancies(){
        Pageable pageable=PageRequest.of(0, 10);
        Page<Vacancy> vacancyPage=vacancyRepository.findAll(pageable);
        vacancyPage.forEach(System.out::println);
    }

    @Test
    void testThatYouCanCountNumberOfVacancies(){
        Long numberOfVacancies= vacancyRepository.count();
        log.info("Number of vacancies:{}", numberOfVacancies);
    }

    @Test
    void testThatYouCanUpdateVacancy() throws VacancyNotFoundException {
        Long id=1L;
        vacancy=vacancyRepository.findById(id)
                .orElseThrow(()->new VacancyNotFoundException("Vacancy Not found"));
        vacancy.setCompanyName("Dansol High School");
        assertDoesNotThrow(()->vacancyRepository.save(vacancy));
        assertThat(vacancy.getCompanyName()).isEqualTo("Dansol High School");
        log.info("Update: {}", vacancy.getCompanyName());
    }

    @Test
    @Rollback(value = false)
    void testThatYouCanDeleteVacancyById() throws VacancyNotFoundException {
        Long id=1L;
        vacancyRepository.deleteById(id);
        Optional<Vacancy> optionalVacancy=vacancyRepository.findById(id);
        if (optionalVacancy.isPresent()){
            throw new VacancyNotFoundException("Vacancy Not Deleted");
        }
    }

    @Test
    @Rollback(value = false)
    void testThatYouCanDeleteAllVacancy(){
        vacancyRepository.deleteAll();
    }
}