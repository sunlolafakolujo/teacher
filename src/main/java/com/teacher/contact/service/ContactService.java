package com.teacher.contact.service;

import com.teacher.contact.model.Contact;
import com.teacher.contact.exception.ContactException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContactService {
    Contact saveContact(Contact contact);
    Contact findContactById(Long id) throws ContactException;
    List<Contact> findAllContact(Integer pageNumber, Integer pageSize);
    Contact updateContact(Contact contact, Long id) throws ContactException;
    void deleteContactById(Long id) throws ContactException;
    void deleteAllContact();
}
