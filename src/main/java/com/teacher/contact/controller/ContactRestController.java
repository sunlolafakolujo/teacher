package com.teacher.contact.controller;

import com.teacher.contact.model.Contact;
import com.teacher.contact.model.ContactDto;
import com.teacher.contact.model.ModifyContact;
import com.teacher.contact.model.NewContact;
import com.teacher.contact.exception.ContactNotFoundException;
import com.teacher.contact.service.ContactService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path ="/api/teacher/contact")
@AllArgsConstructor
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
    public ResponseEntity<ContactDto> getContactById(@PathVariable(value = "id") Long id) throws ContactNotFoundException {
        Contact contact=contactService.findContactById(id);
        ContactDto contactDto=convertContactToDto(contact);
        return new ResponseEntity<>(contactDto, HttpStatus.OK);
    }

    @GetMapping("/findAllContact")
    public ResponseEntity<List<ContactDto>> getAllContact(){

        return new ResponseEntity<>(contactService.findAllContact(0,10)
                .stream()
                .map(this::convertContactToDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @PutMapping("/updateContact/{id}")
    public ResponseEntity<ModifyContact> updateContact(@RequestBody @Valid ModifyContact modifyContact,
                                                       @PathVariable(value = "id") Long id) throws ContactNotFoundException {
        Contact contact=modelMapper.map(modifyContact, Contact.class);
        Contact updateContact=contactService.updateContact(contact, id);
        ModifyContact updatedContact=modelMapper.map(updateContact, ModifyContact.class);
        return new ResponseEntity<>(updatedContact, HttpStatus.OK);
    }

    @DeleteMapping("/deleteContactById/{id}")
    public ResponseEntity<?> deleteContactById(@PathVariable(value = "id") Long id) throws ContactNotFoundException {
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
        contactDto.setId(contact.getId());
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
