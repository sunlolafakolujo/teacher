package com.teacher.contact.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teacher.contact.model.Contact;
import com.teacher.contact.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
class ContactRestControllerTest {

    @Autowired
    private ContactService contactService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    Contact contact;

    @BeforeEach
    void setUp() {
        contact=new Contact();
    }

    @Test
    void testThatWhenYouCallCreateContactMethod_thenContactIsCreated() throws Exception {
        contact.setHouseNumber("17");
        contact.setStreetName("Wakeman Street, Alagomeji");
        contact.setCity("Lagos Island");
        contact.setLandmark("AP Club");
        contact.setStateProvince("Lagos");
        contact.setCountry("Nigeria");

        this.mockMvc.perform(post("/api/contact/saveContact")
                .header(HttpHeaders.AUTHORIZATION, "Bearer ")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(contact)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.streetName", is("Wakeman Street, Alagomeji")))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallGetContactByIdMethod_thenContactIsReturned() throws Exception {

        this.mockMvc.perform(get("/api/contact/findContactById/1")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.streetName").value("Osanyin Street Alagomeji, Yaba"))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallGetAllContactsMethod_thenContactsAreReturned() throws Exception {

        this.mockMvc.perform(get("/api/contact/findAllContact")
                .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$[1].streetName").value("Amuwo Odofin Housing Estate, Mile 2"))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallUpdateContactMethod_thenContactIsUpdated() throws Exception {
        Long id=2L;
        contact=contactService.findContactById(id);
        contact.setHouseNumber("9");
        contact.setStreetName("Ganiat Street, Alahun Osumba, Maza Maza");
        contact.setCity("Amuwo Odofin");
        contact.setLandmark("Monkey Village");
        contact.setStateProvince("Lagos");
        contact.setCountry("Nigeria");

        contactService.updateContact(contact, id);

        this.mockMvc.perform(put("/api/contact/updateContact/2")
                .header(HttpHeaders.AUTHORIZATION, "Bearer ")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(contact)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id").value(contact.getId()))
                .andExpect(jsonPath("$.streetName").value("Ganiat Street, Alahun Osumba, Maza Maza"))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallDeleteContactByIdMethod_thenContactISDeleted() throws Exception {

        this.mockMvc.perform(delete("/api/contact/deleteContactById/2"))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void testThatWhenYouCallDeleteAllContacts_thenContactsAreDeleted() throws Exception {

        this.mockMvc.perform(delete("/api/contact/deleteAllContact"))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}