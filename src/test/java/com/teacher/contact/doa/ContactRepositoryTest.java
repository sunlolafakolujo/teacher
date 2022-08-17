package com.teacher.contact.doa;

import com.teacher.contact.repository.ContactRepository;
import com.teacher.contact.exception.ContactNotFoundException;
import com.teacher.contact.model.Contact;
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
    void testThatYouCanFindContactById() throws ContactNotFoundException {
        Long id=1L;
        contact=contactRepository.findById(id).orElseThrow(()-> new ContactNotFoundException("Contact does not exist"));

        assertEquals(id, contact.getId());

        log.info("contact ID 1 {}", contact);
    }

    @Test
    void testThatYouCanFindAllContacts(){
        List<Contact> contacts=contactRepository.findAll();

        assertNotNull(contacts);

        log.info("All contacts {}", contacts);
    }

    @Test
    void testThatYouCanUpdateContact() throws ContactNotFoundException {
        Long id=1L;
        contact=contactRepository.findById(id).orElseThrow(()-> new ContactNotFoundException("Contact "+id+" does not exist"));

        String stateProvince="Ogun";

        contact.setStateProvince(stateProvince);

        assertDoesNotThrow(()->contactRepository.save(contact));

        assertThat(contact.getStateProvince()).isEqualTo(stateProvince);

        log.info("Updated state {}", contact);
    }

    @Test
    @Rollback(value = false)
    void testThatYouCanDeleteContactById() throws ContactNotFoundException {
        Long id=1L;

        contactRepository.deleteById(id);

        Optional<Contact> optionalContact=contactRepository.findById(id);

        if (optionalContact.isPresent()){
            throw new ContactNotFoundException("Contact "+id+" is not deleted");
        }
    }

    @Test
    @Rollback(value = false)
    void testThatYouCalDeleteAllContacts(){
        contactRepository.deleteAll();
    }
}