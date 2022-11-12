package com.teacher.vacancy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teacher.appuser.exception.AppUserNotFoundException;
import com.teacher.appuser.model.AppUser;
import com.teacher.appuser.service.AppUserService;
import com.teacher.staticdata.JobType;
import com.teacher.vacancy.exception.VacancyNotFoundException;
import com.teacher.vacancy.model.Vacancy;
import com.teacher.vacancy.service.VacancyService;
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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
class VacancyControllerTest {
    
    @Autowired
    private VacancyService vacancyService;
    @Autowired
    private AppUserService appUserService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    Vacancy vacancy;
    AppUser appUser;

    @BeforeEach
    void setUp() {
        vacancy=new Vacancy();
        appUser=new AppUser();
    }

    @Test
    void testThatWhenYouCallCreateVacancyMethod_thenVacancyIsCreated() throws Exception, AppUserNotFoundException {
        Long id=1L;
        appUser=appUserService.findUserById(id);
        vacancy.setAboutUs("ggqssqhh");
        vacancy.setJobSchedule("Mon-Fri, 07:00AM-04:00PM");
        vacancy.setJobType(JobType.FULL_TIME);
        vacancy.setClosingDate(LocalDate.parse("2022-10-21"));
        vacancy.setPublishedDate(LocalDate.parse("2022-10-14"));
        vacancy.setJobTitle("Health Teacher");
        vacancy.setSkillRequirement("ghhhgjhihi");
        vacancy.setKeyResponsibility("hdwhjejhekj");
        vacancy.setBenefit("1. Health Insurance, 2. Leave Allowance, 3. 28 days Vacation");
        vacancy.setAppUser(appUser);
        vacancy.setQualification("sqdeqweeeq");
        vacancy.setJobLocation("Ikeja, Lagos");

        this.mockMvc.perform(post("/api/teacher/vacancy/saveVacancy")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer ")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(vacancy)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.jobTitle", is("Health Teacher")))
                .andExpect(jsonPath("$.jobLocation",is("Ikeja, Lagos")))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallGetVacancyByIdMethod_thenVacancyIsReturned() throws Exception {
        this.mockMvc.perform(get("/api/teacher/vacancy/findVacancyById/{id}",2)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.jobTitle", is("Chemistry Teacher")))
                .andReturn();
    }

    @Test
    void testThatWhenYouCanGetVacancyByTitleMethod_thenVacancyIsOrAreReturned() throws Exception {
        this.mockMvc.perform(get("/api/teacher/vacancy/findVacancyByJobTitle/{jobTitle}","Physic Teacher")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].jobType").value("FULL_TIME"))
                .andExpect(jsonPath("$[0].jobTitle", is("Physic Teacher")))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallGetAllVacanciesMethod_thenVacanciesAreReturned() throws Exception {
        this.mockMvc.perform(get("/api/teacher/vacancy/findAllVacancies")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void testThatWhenYouCallUpdateVacancyMethod_thenVacancyIsUpdated() throws VacancyNotFoundException, Exception {
        Long id=2L;
        vacancy=vacancyService.findVacancyById(id);
        vacancy.setAboutUs("Quality child education got a boost on the 12th day of January, 1998." +
                " It was the day that MIND BUILDERS SCHOOL opened its door to the discerning public," +
                "particularly to parents that place value on and are willing to invest in premium education for their children.\r" +
                "\rFrom a humble beginning of Eighteen (18) pupils and six (6) teaching staff, the school with a vision of building " +
                "the children of tomorrow’s challenge today, by the grace of God has over five hundred (500) pupils and students." +
                " The school has its Nursery and Primary section at Plot 175, Lola Holloway Street, Omole Phase 1." +
                "\r Since commencement in 1998, the school has produced several sets of graduates who are now in either reputable," +
                " colleges and Universities in Nigeria and abroad or professionals in various fields.\r" +
                "\rIn fulfillment of the dreams of the founders of the school, and in compliance with the Federal " +
                "Government policy on education, a purpose-built 40 classroom block to accommodate Nursery, Primary " +
                "and High School has been constructed. It is located at Plot 4, Otunba Jobi Fele Way, Ikeja Central " +
                "Business District, Alausa-Ikeja Lagos. The new building which was opened in July 2008 consist of Multi " +
                "Purpose Hall, Well stocked Library, Classrooms, Basic Science Laboratory, Home Economics Laboratory, " +
                "Physics Laboratory, Chemistry Laboratory, Biology Laboratory, Digital Mathematics Clinic Centre, " +
                "Recovery Room, special rooms for Music, Fine Art, Business Studies and Incormation And Communication " +
                "Technology. Also, In August 2013, we commisioned another schhol building at No 1, Sowande Street, Off Rev." +
                " Emma Adubifa Street, Omole Phase II Isheri, Lagos to accommodate Reception, Nursery, Primary pupils. " +
                "Facilities at the new site include a large playing field for the use of our students.\n" +
                "\rThe school environment offers more than the usual memories of school enthusiasm but team work and " +
                "friendship as well as innate respect for all that are genuinely intellectual. We aim to create a " +
                "disciplined environment where effective teaching and learning can take place. A key feature being proposed " +
                "will be to involve students in decision making process through Student Representative Council.\n" +
                "\rWe place much value on the partnership between the home and the school to help student enjoy their childhood " +
                "and develop into responsible young adults. For this reason, we have to decide to operate a Day Secondary School for now.\n" +
                "\rIt is our prayer that God will provide us with the enablement to lay a solid educational foundation for our " +
                "students to pursue worthwhile future careers.");
        vacancy.setKeyResponsibility("To teach chemistry");
        vacancy.setSkillRequirement("");
        vacancy.setQualification("Bsc Biochemistry or Chemistry");
        vacancy.setBenefit("1. Health Insurance 2. 28 days leave, 3. Training");
        vacancyService.updateVacancy(vacancy, id);
        this.mockMvc.perform(put("/api/teacher/vacancy/updateVacancy/{id}",id)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer ")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(vacancy)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(vacancy.getId()))
                .andExpect(jsonPath("$.benefit", is("1. Health Insurance 2. 28 days leave, 3. Training")))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallDeleteVacancyByIdMethod_thenVacancyIsDeleted() throws Exception {
        this.mockMvc.perform(delete("/api/teacher/vacancy/deleteVacancyById/{id}",1)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void testThatWhenYouCallDeleteAllVacanciesMethod_thenVacanciesAreDeleted() throws Exception {
        this.mockMvc.perform(delete("/api/teacher/vacancy/deleteAllVacancies")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
    }
}