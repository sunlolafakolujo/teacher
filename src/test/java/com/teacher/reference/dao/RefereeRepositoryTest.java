package com.teacher.reference.dao;

import com.teacher.reference.exception.ReferenceNotFoundException;
import com.teacher.reference.model.Referee;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
class RefereeRepositoryTest {

    @Autowired
    private RefereeRepository refereeRepository;

    private  Referee referee;

    @BeforeEach
    void setUp() {
        referee=new Referee();
    }

    @Test
    void testThatYouCanSaveReferee(){
        referee.setFirstName("Adebukola");
        referee.setLastName("Fakolujo");
        referee.setEmail("adebukola@yahoo.com");
        referee.setPhone("08076353671");
        referee.setReferenceLetter("https://eforms.com/recommendation-letter/personal/");

        log.info("Referee repo before saving {}", referee);

        assertDoesNotThrow(()->refereeRepository.save(referee));

        log.info("Referee repo after saving {}", referee);
    }

    @Test
    void testThatYouCanFindRefereeById() throws ReferenceNotFoundException {
        Long id=1L;
        referee=refereeRepository.findById(id)
                .orElseThrow(()->new ReferenceNotFoundException("Reference ID "+id+" Not Found"));

        assertEquals(id, referee.getId());

        log.info("Reference ID 1 {}", referee);
    }

    @Test
    void testThatYouCanFindAllReferences(){
        Pageable page=PageRequest.of(0, 2, Sort.by("firstName")
                .and(Sort.by("lastName")));

        List<Referee> refereeList=refereeRepository.findAll(page).getContent();

        log.info("List of references {}",refereeList);

    }

    @Test
    void testThatYouCanUpdateReferenceById() throws ReferenceNotFoundException {

        Long id=2L;
        referee=refereeRepository.findById(id)
                .orElseThrow(()->new ReferenceNotFoundException("Reference ID "+id+" Not Found"));

        referee.setPhone("09087645350");

        assertDoesNotThrow(()->refereeRepository.save(referee));

        assertEquals("09087645350", referee.getPhone());

        log.info("Updated phone number {}", referee.getPhone());
    }

    @Test
    @Rollback(value = false)
    void testThatYouCanDeleteReferenceById() throws ReferenceNotFoundException {
        Long id=2L;
        refereeRepository.deleteById(id);

        Optional<Referee> optionalReferee=refereeRepository.findById(id);

        if (optionalReferee.isPresent()){
            throw new ReferenceNotFoundException("Reference ID "+id+" is not deleted");
        }
    }

    @Test
    @Rollback(value = false)
    void testThatYouCanDeleteAllReferences(){

        refereeRepository.deleteAll();

    }
}