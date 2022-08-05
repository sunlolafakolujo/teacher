package com.teacher.address.service;

import com.teacher.address.dao.ContactRepository;
import com.teacher.address.entity.Contact;
import com.teacher.address.exception.ContactException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
class ContactServiceImplTest {

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    ContactService contactService=new ContactServiceImpl();

    Contact contact;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        contact=new Contact();
    }

    @Test
    void testThatYouCanMockSaveContactMethod() {
        Mockito.when(contactRepository.save(contact)).thenReturn(contact);

        ArgumentCaptor<Contact> argumentCaptor=ArgumentCaptor.forClass(Contact.class);

        contactService.saveContact(contact);

        Mockito.verify(contactRepository, Mockito.times(1)).save(argumentCaptor.capture());

        Contact capturedContact=argumentCaptor.getValue();

        assertEquals(capturedContact, contact);
    }

    @Test
    void testThatYouCanMockFindContactByIdMethod() throws ContactException {
        Long id=2L;
        Mockito.when(contactRepository.findById(id)).thenReturn(Optional.of(contact));

        contactService.findContactById(id);

        Mockito.verify(contactRepository, Mockito.times(1)).findById(id);
    }

    @Test
    void testThatYouMockFindAllContactMethod() {
//        PageRequest page=PageRequest.of(0, 10);

//        Mockito.when(contactRepository.findAll(page)).thenReturn()
    }

    @Test
    void updateContact() throws ContactException {
        Long id=1L;

        Mockito.when(contactRepository.findById(id)).thenReturn(Optional.of(contact));

        contactService.updateContact(contact, id);

        Mockito.verify(contactRepository, Mockito.times(1)).save(contact);
    }

    @Test
    void deleteContactById() throws ContactException {
        Long id=2L;
        doNothing().when(contactRepository).deleteById(id);

        contactService.deleteContactById(id);

        Mockito.verify(contactRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteAllContact() {

        doNothing().when(contactRepository).deleteAll();

        contactService.deleteAllContact();

        verify(contactRepository, times(1)).deleteAll();
    }
}