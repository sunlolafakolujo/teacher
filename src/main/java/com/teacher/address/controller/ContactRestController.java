package com.teacher.address.controller;

import com.teacher.address.entity.Contact;
import com.teacher.address.entity.ContactDto;
import com.teacher.address.entity.ModifyContact;
import com.teacher.address.entity.NewContact;
import com.teacher.address.exception.ContactException;
import com.teacher.address.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path ="/api/contact")
@RequiredArgsConstructor
public class ContactRestController {

    private final ContactService contactService;

    private final ModelMapper modelMapper;

    @PostMapping("/saveContact")
    public ResponseEntity<NewContact> createContact(@RequestBody @Valid NewContact newContact){
        Contact contact=modelMapper.map(newContact, Contact.class);

        Contact postContact=contactService.saveContact(contact);

        NewContact posted=modelMapper.map(postContact, NewContact.class);

        return new ResponseEntity<>(posted, HttpStatus.CREATED);
    }

    @GetMapping("/findContactById/{id}")
    public ResponseEntity<ContactDto> getContactById(@PathVariable(value = "id") Long id) throws ContactException {

        Contact contact=contactService.findContactById(id);

        ContactDto contactDto=convertContactToDto(contact);

        return new ResponseEntity<>(contactDto, HttpStatus.OK);
    }

    @GetMapping("/findAllContact")
    public ResponseEntity<List<ContactDto>> getAllContact(){
        Pageable pageable= PageRequest.of(0, 10);

        return new ResponseEntity<>(contactService.findAllContact(pageable)
                .stream()
                .map(this::convertContactToDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @PutMapping("/updateContact/{id}")
    public ResponseEntity<ModifyContact> updateContact(@RequestBody @Valid ModifyContact modifyContact,
                                                       @PathVariable(value = "id") Long id) throws ContactException {
        Contact contact=modelMapper.map(modifyContact, Contact.class);

        Contact updateContact=contactService.updateContact(contact, id);

        ModifyContact updatedContact=modelMapper.map(updateContact, ModifyContact.class);

        return new ResponseEntity<>(updatedContact, HttpStatus.OK);
    }

    @DeleteMapping("/deleteContactById/{id}")
    public ResponseEntity<?> deleteContactById(@PathVariable(value = "id") Long id) throws ContactException {
        contactService.deleteContactById(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteAllContact")
    public ResponseEntity<?> deleteAllContact(){
        contactService.deleteAllContact();

        return ResponseEntity.noContent().build();
    }








































    private ContactDto convertContactToDto(Contact contact){

        ContactDto contactDto=new ContactDto();

        contactDto.setStreetNumber(contact.getStreetNumber());
        contactDto.setStreetName(contact.getStreetName());
        contactDto.setCity(contact.getCity());
        contactDto.setPostZipCode(contact.getPostZipCode());
        contactDto.setLandMark(contact.getLandMark());
        contactDto.setStateProvince(contact.getStateProvince());
        contactDto.setCountry(contact.getCountry());

        return contactDto;
    }
}