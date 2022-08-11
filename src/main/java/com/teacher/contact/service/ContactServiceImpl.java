package com.teacher.contact.service;

import com.teacher.contact.dao.ContactRepository;
import com.teacher.contact.model.Contact;
import com.teacher.contact.exception.ContactNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class ContactServiceImpl implements ContactService{

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public Contact findContactById(Long id) throws ContactNotFoundException {
        Contact contact=contactRepository.findById(id).orElseThrow(()-> new ContactNotFoundException("Contact "+id+" Not Found"));

        return contact;
    }

    @Override
    public List<Contact> findAllContact(Integer PageNumber, Integer pageSize) {

        Pageable pageable= PageRequest.of(PageNumber, pageSize);

        Page<Contact> pageResult= contactRepository.findAll(pageable);

        return pageResult.toList();
    }

    @Override
    public Contact updateContact(Contact contact, Long id) throws ContactNotFoundException {
        Contact savedContact=contactRepository.findById(id).orElseThrow(()-> new ContactNotFoundException("Contact "+id+" Not Found"));

        if (Objects.nonNull(contact.getStreetNumber()) || "".equalsIgnoreCase(contact.getStreetNumber())){
            savedContact.setStreetNumber(contact.getStreetNumber());
        }if (Objects.nonNull(contact.getStreetName())||"".equalsIgnoreCase(contact.getStreetName())){
            savedContact.setStreetName(contact.getStreetName());
        }if (Objects.nonNull(contact.getCity())||"".equalsIgnoreCase(contact.getCity())){
            savedContact.setCity(contact.getCity());
        }if (Objects.nonNull(contact.getPostZipCode())||"".equalsIgnoreCase(contact.getPostZipCode())){
            savedContact.setPostZipCode(contact.getPostZipCode());
        }if (Objects.nonNull(contact.getLandMark())||"".equalsIgnoreCase(contact.getLandMark())){
            savedContact.setLandMark(contact.getLandMark());
        }if (Objects.nonNull(contact.getStateProvince()) || "".equalsIgnoreCase(contact.getStateProvince())){
            savedContact.setStateProvince(contact.getStateProvince());
        }if (Objects.nonNull(contact.getCountry())||"".equalsIgnoreCase(contact.getCountry())){
            savedContact.setCountry(contact.getCountry());
        }

        return contactRepository.save(savedContact);
    }

    @Override
    public void deleteContactById(Long id) throws ContactNotFoundException {
        contactRepository.deleteById(id);

        Optional<Contact> optionalContact=contactRepository.findById(id);

        if (optionalContact.isEmpty()){
            log.info("Contact is deleted");
        }else throw new ContactNotFoundException("Contact ID "+id+" Not Deleted");

    }

    @Override
    public void deleteAllContact() {
        contactRepository.deleteAll();
    }
}
