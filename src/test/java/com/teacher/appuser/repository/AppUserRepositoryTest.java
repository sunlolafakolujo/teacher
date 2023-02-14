package com.teacher.appuser.repository;

import com.teacher.appuser.exception.AppUserNotFoundException;
import com.teacher.appuser.model.AppUser;
import com.teacher.contact.model.Contact;
import com.teacher.qualification.model.Qualification;
import com.teacher.reference.model.Referee;
import com.teacher.staticdata.*;
import com.teacher.userrole.exception.UserRoleNotFoundException;
import com.teacher.userrole.model.UserRole;
import com.teacher.userrole.repository.UserRoleRepository;
import com.teacher.workexperience.model.WorkExperience;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

import static com.teacher.staticdata.UserType.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
@Transactional
class AppUserRepositoryTest {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    AppUser appUser;

    Qualification qualification;

    WorkExperience workExperience;

    Referee referee;

    Contact contact;

    UserRole userRole;

    @BeforeEach
    void setUp() {
        appUser=new AppUser();
        qualification=new Qualification();
        workExperience=new WorkExperience();
        referee=new Referee();
        contact=new Contact();
        userRole=new UserRole();
    }

    @Test
    void testThatYouCanSaveUser() throws UserRoleNotFoundException {

        qualification.setCurrentQualification(Status.NO);
        qualification.setEndDate(LocalDate.parse("2001-03-30"));
        qualification.setStartDate(LocalDate.parse("1997-09-15"));
        qualification.setSubject("Physics");
        qualification.setDegreeTitle("Bachelor of Education");
        qualification.setClassOfDegree("Second Class Upper");
        qualification.setInstitutionAddress("Ijanikin, Lagos-Badagry");
        qualification.setInstitutionName("College of Education");
        qualification.setInstitutionAddress("Ojo");

        workExperience.setCompany("Mind Builder School");
        workExperience.setPosition("Teacher");
        workExperience.setCurrentWork(Status.YES);
        workExperience.setStartDate(LocalDate.parse("2005-10-05"));

        referee.setFirstName("Adelola");
        referee.setLastName("Akinradewo");
        referee.setEmail("aa@yahoo.com");
        referee.setPhone("08076546845");
        contact.setHouseNumber("2A");
        contact.setStreetName("Akinpetu Street Alagomeji");
        contact.setCity("Lagos Island");
        contact.setLandMark("Algomeji Bus stop");
        contact.setStateProvince("Lagos");
        contact.setCountry("Nigeria");
        List<Contact> contacts=new ArrayList<>();
        contacts.add(contact);

        userRole=userRoleRepository.findByRoleName("TEACHER")
                .orElseThrow(()->new UserRoleNotFoundException("Role Not Found"));

        appUser.setUserType(TEACHER);
        appUser.setUserId("TCH-".concat(String.valueOf(new Random().nextInt(100000000))));
        appUser.setPassword(passwordEncoder.encode("1234"));
        appUser.setEmail("kafolayan@gmail.com");
        appUser.setMobile("08097654579");
        appUser.setIsEnabled(true);
        appUser.setUserRoles(List.of(userRole));
        appUser.setContact(contact);

        log.info("AppUser repo before saving: {}", appUser);

        assertDoesNotThrow(()->appUserRepository.save(appUser));

        log.info("AppUser repo after saving: {}", appUser);
    }

    @Test
    void testThatYouCanFindUserById() throws AppUserNotFoundException {
        Long id=2L;
        appUser=appUserRepository.findById(id)
                .orElseThrow(()->new AppUserNotFoundException("User with ID "+id+" Not Found"));
        log.info("User: {} ", appUser);
    }

    @Test
    void testThatYouCanFindByUserType(){
        UserType userType= TEACHER;
        Pageable pageable=PageRequest.of(0, 10);
        List<AppUser> appUsers=appUserRepository.findByUserType(userType, pageable);
        appUsers.forEach(System.out::println);
//        log.info("User(s) teachers: {}", appUsers);
    }




    @Test
    void testThatYouCanFindUserByEmail() throws AppUserNotFoundException {
        String searchKey="mbs@gmail.com";
        appUser=appUserRepository.findByUsernameOrEmailOrMobile(searchKey,searchKey,searchKey,searchKey)
                .orElseThrow(()-> new AppUserNotFoundException("User email "+searchKey+" Not Found"));
        log.info("User(s) with email: {}", appUser);
    }

    @Test
    void testThatYouCanFindAllUsers(){
        Pageable pageable=PageRequest.of(0, 10);
        Page<AppUser> appUsers=appUserRepository.findAll(pageable);
        appUsers.forEach(System.out::println);
//        log.info("Users: {}", appUsers.toList());
    }

    @Test
    void testThatYouCanFindAllUserById(){
        List<Long> idList= Arrays.asList(1L,2L,3L);
        List<AppUser> appUsers=appUserRepository.findAllById(idList);
        appUsers.forEach(System.out::println);
//        log.info("All users", appUsers);
    }

    @Test
    void testThatYouCanUpdateUser() throws AppUserNotFoundException {
        Long id=2L;
        appUser=appUserRepository.findById(id)
                .orElseThrow(()->new AppUserNotFoundException("User Not Found"));
        appUser.setPassword("kokoma_123");
        assertDoesNotThrow(()->appUserRepository.save(appUser));
        assertEquals("kokoma_123", appUser.getPassword());
        log.info("New password: {}", appUser.getPassword());
    }

    @Rollback(value = false)
    @Test
    void testThatYouCanDeleteUserById() throws AppUserNotFoundException {
        Long id=1L;
        appUserRepository.deleteById(id);
        Optional<AppUser> optionalAppUser=appUserRepository.findById(id);
        if (optionalAppUser.isPresent()){
            throw new AppUserNotFoundException("User ID "+id+" Not Deleted");
        }
    }

    @Rollback(value = false)
    @Test
    void testThatYouCanDeleteUsers() {
        appUserRepository.deleteAll();
    }

}