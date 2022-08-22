package com.teacher.appuser.repository;

import com.teacher.appuser.model.AppUser;
import com.teacher.contact.model.Contact;
import com.teacher.qualification.model.Qualification;
import com.teacher.qualification.repository.QualificationRepository;
import com.teacher.reference.dao.RefereeRepository;
import com.teacher.reference.model.Referee;
import com.teacher.staticdata.*;
import com.teacher.userrole.model.UserRole;
import com.teacher.workexperience.model.WorkExperience;
import com.teacher.workexperience.repository.WorkExperienceRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
@Transactional
class AppUserRepositoryTest {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    AppUser appUser;

    Qualification qualification;

    WorkExperience workExperience;

    Referee referee;

    Contact contact;

    UserRole userRole;

    @BeforeEach
    void setUp() {
        appUser=new AppUser();
        qualification=new Qualification();
        workExperience=new WorkExperience();
        referee=new Referee();
        contact=new Contact();
        userRole=new UserRole();
    }

    @Test
    void testThatYouCanSaveUser(){

        qualification.setCurrentQualification(Status.NO);
        qualification.setEndDate(LocalDate.parse("2001-03-30"));
        qualification.setStartDate(LocalDate.parse("1997-09-15"));
        qualification.setSubject("Physics");
        qualification.setDegreeTitle("Bachelor of Education");
        qualification.setClassOfDegree("Second Class Upper");
        qualification.setInstitutionAddress("Ijanikin, Lagos-Badagry");
        qualification.setSchoolName("College of Education");

        workExperience.setCompany("Mind Builder School");
        workExperience.setPosition("Teacher");
        workExperience.setCurrentWork(Status.YES);
        workExperience.setStartDate(LocalDate.parse("2005-10-05"));

        referee.setFirstName("Adelola");
        referee.setLastName("Akinradewo");
        referee.setEmail("aa@yahoo.com");
        referee.setPhone("08076546845");
        referee.setReferenceLetter("http://tyuyutuioo.com");

        contact.setStreetNumber("2A");
        contact.setStreetName("Akinpetu Street Alagomeji");
        contact.setCity("Lagos Island");
        contact.setLandMark("Algomeji Bus stop");
        contact.setPostZipCode("110000");
        contact.setStateProvince("Lagos");
        contact.setCountry("Nigeria");

        userRole.setRoleName("TEACHER");

        appUser.setUserType(UserType.TEACHER);
        appUser.setFirstName("Kunle");
        appUser.setLastName("Afolayan");
        appUser.setUsername("kafolayan");
        appUser.setPassword(passwordEncoder.encode("1234"));
        appUser.setDateOfBirth(LocalDate.parse("1980-02-10"));
        appUser.setAge(Period.between(LocalDate.now(), appUser.getDateOfBirth()).getYears());
        appUser.setEmail("kafolayan@gmail.com");
        appUser.setPhone("08097654579");
        appUser.setEnabled(true);
        appUser.setUserRoles(List.of(userRole));
        appUser.setMeansOfIdentification(MeansOfIdentification.NIN);
        appUser.setMeansOfIdentificationIssueDate(LocalDate.parse("2020-07-04"));
        appUser.setMeansOfIdentificationExpiryDate(LocalDate.parse("2024-07-04"));
        appUser.setGender(Gender.MALE);
        appUser.setTitle(Title.MR);
        appUser.setPicUrl("http://tyuio.com");
        appUser.setContact(contact);
        appUser.setQualifications(List.of(qualification));
        appUser.setWorkExperiences(List.of(workExperience));
        appUser.setReferees(List.of(referee));

        log.info("AppUser repo before saving: {}", appUser);

        assertDoesNotThrow(()->appUserRepository.save(appUser));

        log.info("AppUser repo after saving: {}", appUser);
    }

    @Test
    void testThatYoCanFindUserById(){
        

    }
}