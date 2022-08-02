package com.teacher.address.service;

import com.teacher.address.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContactService {
    Contact saveContact(Contact contact);
    Contact findContactById(Long id);
    Page<Contact> findAllContact(Pageable pageable);
    Contact updateContact(Contact contact, Long id);
    void deleteContactById(Long id);
    void deleteAllContact();
}
