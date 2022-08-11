package com.teacher.qualification.doa;

import com.teacher.contact.dao.ContactRepository;
import com.teacher.contact.exception.ContactException;
import com.teacher.contact.model.Contact;
import com.teacher.qualification.exception.QualificationException;
import com.teacher.qualification.model.Qualification;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
@Transactional
class QualificationRepositoryTest {

    @Autowired
    private QualificationRepository qualificationRepository;

    @Autowired
    private ContactRepository contactRepository;

    Qualification qualification;

    Contact contact;

    @BeforeEach
    void setUp() {
        qualification=new Qualification();
        contact=new Contact();
    }

    @Test
    void testThatYouCanSaveQualification() throws ContactException {
        Long id=1L;
        contact=contactRepository.findById(id).orElseThrow(()->new ContactException("Contact "+id+" Not Found"));

        qualification.setContact(contact);
        qualification.setSubject("Mechanical Engineering");
        qualification.setDegreeTitle("Bachelor of Science");
        qualification.setClassOfDegree("2nd Class Upper");
        qualification.setSchool("Lagos State University of Technology");

        log.info("Qualification before saving{}", qualification);

        assertDoesNotThrow(()->qualificationRepository.save(qualification));

        log.info("Qualification after saving{}", qualification);
    }

    @Test
    void testThatYouCanFindQualificationById() throws QualificationException {
        Long id=2L;

        qualification=qualificationRepository.findById(id)
                .orElseThrow(()->new QualificationException("Qualification "+id+" Not found"));

        assertEquals(2, qualification.getId());

        log.info("Qualification ID 2{}", qualification);
    }

    @Test
    void testThatYouCanFindAllQualifications(){

        List<Qualification> qualifications=qualificationRepository.findAll();

        log.info("All qualifications{}", qualifications);

        log.info("Number of qualifications in the db: {}", qualifications.stream().count()); //same result as below.

        log.info("Number of qualifications in the db: {}", qualifications.size());
    }

    @Test
    @Rollback(false)
    void testThatYouCanDeleteQualification() throws QualificationException {
        Long id=2L;

        qualificationRepository.deleteById(id);

        Optional<Qualification> optionalQualification=qualificationRepository.findById(id);

        if (optionalQualification.isEmpty()){
            log.info("Qualification ID "+id+" is deleted");
        }else throw new QualificationException("Qualification ID"+id+" is not deleted");
    }

    @Test
    @Rollback(value = false)
    void testThatYouCanDeleteAllQualification(){
        qualificationRepository.deleteAll();
    }

}