package com.teacher.reference.dao;

import com.teacher.reference.model.Referee;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

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
}