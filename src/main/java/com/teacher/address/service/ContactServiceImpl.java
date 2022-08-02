package com.teacher.address.service;

import com.teacher.address.dao.ContactRepository;
import com.teacher.address.entity.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService{

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public Contact findContactById(Long id) {
        Contact contact=contactRepository.findById(id).orElseThrow(()-> new RuntimeException("Contact "+id+" does not exist"));

        return contact;
    }

    @Override
    public Page<Contact> findAllContact(Pageable pageable) {
        pageable= PageRequest.of(0, 10);

        return contactRepository.findAll(pageable);
    }

    @Override
    public Contact updateContact(Contact contact, Long id) {
        return null;
    }

    @Override
    public void deleteContactById(Long id) {

    }

    @Override
    public void deleteAllContact() {

    }
}
