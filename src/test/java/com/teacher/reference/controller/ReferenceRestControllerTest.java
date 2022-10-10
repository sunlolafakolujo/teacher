package com.teacher.reference.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teacher.reference.exception.ReferenceNotFoundException;
import com.teacher.reference.model.Referee;
import com.teacher.reference.service.ReferenceService;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
@Sql(scripts ={"classpath:db/insert.sql"})
class ReferenceRestControllerTest {

    @Autowired
    private ReferenceService referenceService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    Referee referee;

    @BeforeEach
    void setUp() {
        referee=new Referee();
    }

    @Test
    void testThatWhenYouCallSaveReference_thenReferenceIsSaved() throws Exception {
        referee.setFirstName("Akinolu");
        referee.setLastName("Akintoye");
        referee.setEmail("aa@yahoo.com");
        referee.setPhone("08076524212");
        referee.setReferenceLetterUrl("https://www.independent.co.uk/space/photographer-sun-picture-detail-resolution-b1971193.html");

        this.mockMvc.perform(post("/api/referee/saveReference")
                .header(AUTHORIZATION, "Bearer ")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(referee)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.firstName").value("Akinolu"))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallGetReferenceByIdMethod_thenReferenceIsReturned() throws Exception {
        this.mockMvc.perform(get("/api/referee/findReferenceById/2")
                        .header(AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.firstName", is("Tokunbo")))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallGetAllReferencesMethod_thenReferencesAreReturned() throws Exception {

        this.mockMvc.perform(get("/api/referee/findAllReferences")
                .header(AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$[1].lastName", is("Akeju")))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallUpdateReferenceMethod_thenReferenceIsUpdated() throws ReferenceNotFoundException, Exception {

        Long id=2L;
        referee=referenceService.findRefereeById(id);

        referee.setEmail("takeju@yahoo.com");

        referenceService.updateReferee(referee, id);

        this.mockMvc.perform(put("/api/referee/updateReference/2")
                .header(AUTHORIZATION, "Bearer ")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(referee)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(referee.getId()))
                .andExpect(jsonPath("$.email", is("takeju@yahoo.com")))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallDeleteReferenceByIdMethod_thenReferenceIsDeleted() throws Exception {

        this.mockMvc.perform(delete("/api/referee/deleteReferenceById/1")
                .header(AUTHORIZATION, "Bearer "))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void testThatWhenYouCallDeleteAllReferencesMethod_thenReferencesAreDeleted() throws Exception {
        this.mockMvc.perform(delete("/api/referee/deleteAllReferences")
                        .header(AUTHORIZATION, "Bearer "))
                .andExpect(status().isNoContent())
                .andReturn();

    }
}