package com.teacher.contact.service;

import com.teacher.contact.repository.ContactRepository;
import com.teacher.contact.model.Contact;
import com.teacher.contact.exception.ContactNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class ContactServiceImpl implements ContactService{

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public Contact findContactById(Long id) throws ContactNotFoundException {
        Contact contact=contactRepository.findById(id)
                .orElseThrow(()-> new ContactNotFoundException("Contact "+id+" Not Found"));
        return contact;
    }

    @Override
    public List<Contact> findAllContact(Integer PageNumber) {
        Pageable pageable= PageRequest.of(PageNumber, 10);
        return contactRepository.findAll(pageable).toList();
    }

    @Override
    public Contact updateContact(Contact contact, Long id) throws ContactNotFoundException {
        Contact savedContact=contactRepository.findById(id)
                .orElseThrow(()-> new ContactNotFoundException("Contact "+id+" Not Found"));
        if (Objects.nonNull(contact.getHouseNumber()) && !"".equalsIgnoreCase(contact.getHouseNumber())){
            savedContact.setHouseNumber(contact.getHouseNumber());
        }if (Objects.nonNull(contact.getStreetName()) && !"".equalsIgnoreCase(contact.getStreetName())){
            savedContact.setStreetName(contact.getStreetName());
        }if (Objects.nonNull(contact.getCity()) && !"".equalsIgnoreCase(contact.getCity())){
            savedContact.setCity(contact.getCity());
        }if (Objects.nonNull(contact.getLandmark()) && !"".equalsIgnoreCase(contact.getLandmark())){
            savedContact.setLandmark(contact.getLandmark());
        }if (Objects.nonNull(contact.getStateProvince()) && !"".equalsIgnoreCase(contact.getStateProvince())){
            savedContact.setStateProvince(contact.getStateProvince());
        }if (Objects.nonNull(contact.getCountry()) && !"".equalsIgnoreCase(contact.getCountry())){
            savedContact.setCountry(contact.getCountry());
        }
        return contactRepository.save(savedContact);
    }

    @Override
    public void deleteContactById(Long id) throws ContactNotFoundException {
        if (contactRepository.existsById(id)){
            contactRepository.deleteById(id);
        }else {
            throw new ContactNotFoundException("Contact ID "+id+" Not Found");
        }
    }

    @Override
    public void deleteAllContact() {
        contactRepository.deleteAll();
    }
}
