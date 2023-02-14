package com.teacher.appuser.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teacher.appuser.exception.AppUserNotFoundException;
import com.teacher.appuser.model.AppUser;
import com.teacher.appuser.service.AppUserService;
import com.teacher.contact.model.Contact;
import com.teacher.contact.service.ContactService;
import com.teacher.qualification.model.Qualification;
import com.teacher.qualification.service.QualificationService;
import com.teacher.reference.model.Referee;
import com.teacher.reference.service.ReferenceService;
import com.teacher.staticdata.Gender;
import com.teacher.staticdata.MeansOfIdentification;
import com.teacher.staticdata.Status;
import com.teacher.staticdata.Title;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
class AppUserSearchRestControllerTest {
    @Autowired
    private AppUserService appUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ContactService contactService;

    @Autowired
    private ReferenceService referenceService;

    @Autowired
    private QualificationService qualificationService;

    @Autowired
    private WorkExperienceService workExperienceService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    AppUser appUser;
    Contact contact;
    Referee referee;
    Qualification qualification;
    WorkExperience workExperience;

    @BeforeEach
    void setUp() {
        appUser=new AppUser();
        contact=new Contact();
        referee=new Referee();
        qualification=new Qualification();
        workExperience=new WorkExperience();
    }

    @Test
    void testThatWhenYouCallSchoolRegistrationMethod_thenSchoolIsRegistered() throws Exception {
        contact.setHouseNumber("23");
        contact.setStreetName("Coast Street Ebute Metta");
        contact.setCity("Lagos Mainland");
        contact.setLandMark("Oyingbo");
        contact.setStateProvince("Lagos");
        contact.setCountry("Nigeria");

        appUser.setPassword("1234");
        appUser.setUsername("okeOdo");
        appUser.setEmail("okeodo@gmail.com");
        appUser.setMobile("09087554562");
        appUser.setContact(contact);

        this.mockMvc.perform(post("/api/teacher/appUser/schoolRegistration")
                .header(HttpHeaders.AUTHORIZATION, "Bearer ")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(appUser)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.schoolName", is("Oke Odo High School")))
                .andReturn();
    }
    @Test
    void testThatWhenYouCallTeacherRegistrationMethod_thenTeacherIsSaved() throws Exception {
        contact.setHouseNumber("40");
        contact.setStreetName("Oko Awo Street");
        contact.setCity("Lagos Island");
        contact.setLandMark("Gorodom");
        contact.setStateProvince("Lagos");
        contact.setCountry("Nigeria");
        List<Contact> contacts=new ArrayList<>();
        contacts.add(contact);

        qualification.setInstitutionName("Federal College of Education");
        qualification.setDegreeTitle("Diploma Physic Education");
        qualification.setClassOfDegree("Upper Credit");
        qualification.setSubject("Physic");
        qualification.setCurrentQualification(Status.NO);
        qualification.setStartDate(LocalDate.parse("2001-08-10"));
        qualification.setEndDate(LocalDate.parse("2005-07-20"));
        qualification.setInstitutionAddress("2, Akoka Road, Yaba");
        Set<Qualification> qualifications=new HashSet<>();
        qualifications.add(qualification);

        workExperience.setCompany("Finbars College");
        workExperience.setPosition("Head Teacher");
        workExperience.setCurrentWork(Status.YES);
        workExperience.setStartDate(LocalDate.parse("2007-01-04"));
        Set<WorkExperience> workExperiences=new HashSet<>();
        workExperiences.add(workExperience);

        referee.setTitle(Title.MR);
        referee.setFirstName("Adeolu");
        referee.setLastName("Oyenusi");
        referee.setPhone("08145678885");
        referee.setEmail("ao@gmail.com");
        Referee referee1=new Referee();
        referee1.setTitle(Title.MRS);
        referee1.setFirstName("Olubunmi");
        referee1.setLastName("Danmola");
        referee1.setPhone("08075412398");
        referee1.setEmail("odanmola@yahoo.com");
        Set<Referee> referees=new HashSet<>();
        referees.add(referee);
        referees.add(referee1);


        appUser.setUsername("olusinaayankunle");
        appUser.setPassword("1234");
        appUser.setEmail("olusinaayankunle@gmail.com");
        appUser.setMobile("09065346791");

        this.mockMvc.perform(post("/api/teacher/appUser/teacherRegistration")
                .header(HttpHeaders.AUTHORIZATION, "Bearer ")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(appUser)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.meansOfIdentificationRefNumber", is("FG97579J589K")))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallParentRegistrationMethod_thenParentIsRegistered() throws Exception {
        contact.setHouseNumber("6");
        contact.setStreetName("Osanyin Street Alagomeji");
        contact.setCity("Lagos Island");
        contact.setLandMark("Rowpark");
        contact.setStateProvince("Lagos");
        contact.setCountry("Nigeria");
        List<Contact>contacts=new ArrayList<>();
        contacts.add(contact);

        referee.setTitle(Title.MRS);
        referee.setFirstName("Adebola");
        referee.setLastName("Adelani");
        referee.setPhone("09056898543");
        referee.setEmail("aadelani@yahoo.com");
        Set<Referee>referees=new HashSet<>();
        referees.add(referee);


        appUser.setUsername("oadejumo");
        appUser.setPassword("1234");
        appUser.setEmail("oadejumo@gmail.com");
        appUser.setMobile("07086456781");
        appUser.setContact(contact);

        this.mockMvc.perform(post("/api/teacher/appUser/parentRegistration")
                .header(HttpHeaders.AUTHORIZATION, "Bearer ")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(appUser)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.meansOfIdentificationRefNumber", is("A0457899")))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallGetUserByIdMethod_thenUserIsReturned() throws Exception {
        this.mockMvc.perform(get("/api/teacher/appUser/findUserById/{id}",2)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.userType").value("PARENT"))
                .andExpect(jsonPath("$.firstName", is("Kemi")))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallGetAllUsersMethod_thenUsersAreReturned() throws Exception {
        this.mockMvc.perform(get("/api/teacher/appUser/findAllUsers")
                .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$[0].userType").value("SCHOOL"))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallGetUserByTypeMethod_thenUserIsOrUsersAreReturned() throws Exception {
        this.mockMvc.perform(get("/api/teacher/appUser/findUserByType/{userType}","TEACHER")
                .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.*",hasSize(1)))
                .andExpect(jsonPath("$[0].userType").value("TEACHER"))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallGetUserByEmailMethod_thenUserIsReturned() throws Exception {
        this.mockMvc.perform(get("/api/teacher/appUser/findUserByEmail/{email}","kemiadebiyi@yahoo.com")
                .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.email", is("kemiadebiyi@yahoo.com")))
                .andExpect(jsonPath("$.firstName", is("Kemi")))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallGetUserByPhoneMethod_thenUserIsReturned() throws Exception {
        this.mockMvc.perform(get("/api/teacher/appUser/findUserByPhone/{phone}","08097643451")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.email", is("aa@yahoo.com")))
                .andExpect(jsonPath("$.firstName", is("Adeola")))
                .andExpect(jsonPath("$.phone", is("08097643451")))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallGetUserByFirstNameMethod_thenUserIsOrAreReturned() throws Exception {
        this.mockMvc.perform(get("/api/teacher/appUser/findUserByFirstName/{firstName}","Kemi")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].email", is("kemiadebiyi@yahoo.com")))
                .andExpect(jsonPath("$[0].firstName", is("Kemi")))
                .andExpect(jsonPath("$[0].phone", is("08097643551")))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallGetUserByLastNameMethod_thenUserIsOrAreReturned() throws Exception {
        this.mockMvc.perform(get("/api/teacher/appUser/findUserByLastName/{lastName}","Adebiyi")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].email", is("kemiadebiyi@yahoo.com")))
                .andExpect(jsonPath("$[0].firstName", is("Kemi")))
                .andExpect(jsonPath("$[0].phone", is("08097643551")))
                .andReturn();
    }

    @Test
    void testThatWhenYOuCallGetUserBySchoolNameMethod_thenUserIsReturn() throws Exception {
        this.mockMvc.perform(get("/api/teacher/appUser/findUserBySchoolName/{schoolName}","Mind Builders School")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].email", is("mbs@gmail.com")))
                .andExpect(jsonPath("$[0].schoolName", is("Mind Builders School")))
                .andExpect(jsonPath("$[0].phone", is("09087537751")))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallUpdateUserMethod_thenUserIsUpdated() throws AppUserNotFoundException, Exception {
        Long id=3L;
        appUser=appUserService.findUserById(id);
        appUser.setPassword(passwordEncoder.encode("12345"));
        appUserService.updateUser(appUser, id);
        this.mockMvc.perform(put("/api/teacher/appUser/updateUser/{id}",id)
                .header(HttpHeaders.AUTHORIZATION, "Bearer ")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(appUser)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(appUser.getId()))
                .andExpect(jsonPath("$.firstName", is("Adeola")))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallDeleteUserByIdMethod_thenUserIsDeleted() throws Exception {
        this.mockMvc.perform(delete("/api/teacher/appUser/deleteUserById/{id}",3)
                .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void testThatWhenYouCallDeleteAllUsersMethod_thenUsersAreDeleted() throws Exception {
        this.mockMvc.perform(delete("/api/teacher/appUser/deleteAllUsers")
                .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
    }
}