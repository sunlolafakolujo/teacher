package com.teacher.address.service;

import com.teacher.address.dao.ContactRepository;
import com.teacher.address.entity.Contact;
import com.teacher.address.exception.ContactException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public Contact findContactById(Long id) throws ContactException {
        Contact contact=contactRepository.findById(id).orElseThrow(()-> new ContactException("Contact "+id+" Not Found"));

        return contact;
    }

    @Override
    public Page<Contact> findAllContact(Pageable pageable) {
        pageable= PageRequest.of(0, 10);

        return contactRepository.findAll(pageable);
    }

    @Override
    public Contact updateContact(Contact contact, Long id) throws ContactException {
        Contact savedContact=contactRepository.findById(id).orElseThrow(()-> new ContactException("Contact "+id+" Not Found"));

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
    public void deleteContactById(Long id) throws ContactException {
        contactRepository.deleteById(id);

        Optional<Contact> optionalContact=contactRepository.findById(id);

        if (optionalContact.isEmpty()){
            log.info("Contact is deleted");
        }else throw new ContactException("Contact ID "+id+" Not Deleted");

    }

    @Override
    public void deleteAllContact() {
        contactRepository.deleteAll();
    }
}
