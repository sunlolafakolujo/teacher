package com.teacher.address.service;

import com.teacher.address.entity.Contact;
import com.teacher.address.exception.ContactException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContactService {
    Contact saveContact(Contact contact);
    Contact findContactById(Long id) throws ContactException;
    Page<Contact> findAllContact(Pageable pageable);
    Contact updateContact(Contact contact, Long id) throws ContactException;
    void deleteContactById(Long id) throws ContactException;
    void deleteAllContact();
}
