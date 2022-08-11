package com.teacher.contact.service;

import com.teacher.contact.model.Contact;
import com.teacher.contact.exception.ContactNotFoundException;

import java.util.List;

public interface ContactService {
    Contact saveContact(Contact contact);
    Contact findContactById(Long id) throws ContactNotFoundException;
    List<Contact> findAllContact(Integer pageNumber, Integer pageSize);
    Contact updateContact(Contact contact, Long id) throws ContactNotFoundException;
    void deleteContactById(Long id) throws ContactNotFoundException;
    void deleteAllContact();
}
