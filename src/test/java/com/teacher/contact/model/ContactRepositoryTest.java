package com.teacher.contact.entity;

import com.teacher.contact.dao.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
class ContactRepositoryTest {

    @Autowired
    private ContactRepository contactRepository;

    Contact contact;

    @BeforeEach
    void setUp() {
        contact=new Contact();
    }

    @Test
    void testThatYouCanSaveContact(){
        contact.setStreetNumber("2A");
        contact.setStreetName("Animashaun Street Ijesha Tedo Surulere");
        contact.setCity("Surulere");
        contact.setStateProvince("Lagos");
        contact.setCountry("Nigeria");

        log.info("Contact repo before saving {}", contact);

        assertDoesNotThrow(()->contactRepository.save(contact));

        assertEquals("2A", contact.getStreetNumber());

        log.info("Contact repo before saving {}", contact);
    }

    @Test
    void testThatYouCanFindContactById(){
        Long id=1L;
        contact=contactRepository.findById(id).orElseThrow(()-> new RuntimeException("Contact does not exist"));

        assertEquals(id, contact.getId());

        log.info("contact ID 1 {}", contact);
    }

    @Test
    void testThatYouCanFindAllcontacts(){
        List<Contact> contacts=contactRepository.findAll();

        assertNotNull(contacts);

        log.info("All contacts {}", contacts);
    }

    @Test
    void testThatYouCanUpdateContact(){
        Long id=1L;
        contact=contactRepository.findById(id).orElseThrow(()-> new RuntimeException("Contact "+id+" does not exist"));

        String state_province="Ogun";

        contact.setStateProvince(state_province);

        assertDoesNotThrow(()->contactRepository.save(contact));

        assertThat(contact.getStateProvince()).isEqualTo(state_province);

        log.info("Updated state {}", contact);
    }

    @Test
    @Rollback(value = false)
    void testThatYouCanDeleteContactById(){
        Long id=2L;

        contactRepository.deleteById(id);

        Optional<Contact> optionalContact=contactRepository.findById(id);

        if (optionalContact.isPresent()){
            throw new RuntimeException("Contact "+id+" is not deleted");
        }
    }

    @Test
    @Rollback(value = false)
    void testThatYouCalDeleteAllContacts(){
        contactRepository.deleteAll();
    }
}