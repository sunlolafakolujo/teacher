package com.teacher.vacancy.service;

import com.teacher.appuser.exception.AppUserNotFoundException;
import com.teacher.appuser.model.AppUser;
import com.teacher.appuser.repository.AppUserRepository;
import com.teacher.vacancy.exception.VacancyNotFoundException;
import com.teacher.vacancy.model.Vacancy;
import com.teacher.vacancy.repository.VacancyRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
class VacancyServiceImplTest {
    @Mock
    private VacancyRepository vacancyRepository;

    @Mock
    private AppUserRepository appUserRepository;

    @InjectMocks
    private VacancyService vacancyService=new VacancyServiceImpl();

    Vacancy vacancy;
    AppUser appUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        vacancy=new Vacancy();
        appUser=new AppUser();
    }

    @Test
    void testThatYouCanMockSaveVacancyMethod() {
        when(vacancyRepository.save(vacancy)).thenReturn(vacancy);
        vacancyService.saveVacancy(vacancy);
        ArgumentCaptor<Vacancy> vacancyArgumentCaptor=ArgumentCaptor.forClass(Vacancy.class);
        verify(vacancyRepository, times(1)).save(vacancyArgumentCaptor.capture());
        Vacancy capturedVacancy=vacancyArgumentCaptor.getValue();
        assertEquals(capturedVacancy,vacancy);
    }

    @Test
    void testThatYouCanMockFindVacancyByIdMethod() throws VacancyNotFoundException {
        Long id=2L;
        when(vacancyRepository.findById(id)).thenReturn(Optional.of(vacancy));
        vacancyService.findVacancyById(id);
        verify(vacancyRepository, times(1)).findById(id);
    }

//    @Test
//    void testThatYouCanMockFindVacancyByJobIdMethod() throws VacancyNotFoundException {
//        String jobId="G102";
//        when(vacancyRepository.findByJobId(jobId)).thenReturn(vacancy);
//        vacancyService.findVacancyByJobId(jobId);
//        verify(vacancyRepository, times(1)).findByJobId(jobId);
//    }

    @Test
    void testThatYouCanMockFindVacancyByJobTitleMethod() throws VacancyNotFoundException {
        String jobTitle="Head Teacher";
        Pageable pageable= PageRequest.of(0, 10);
        List<Vacancy> vacancies=new ArrayList<>();
        when(vacancyRepository.findByJobTitle(jobTitle, pageable)).thenReturn(vacancies);
        vacancyService.findVacancyByJobTitle(jobTitle, pageable);
        verify(vacancyRepository, times(1)).findByJobTitle(jobTitle, pageable);
    }

    @Test
    void testThatYouCanMockFindAllVacanciesMethod() {
        Pageable pageable=PageRequest.of(0, 10);
        List<Vacancy> vacancies=new ArrayList<>();
        Page<Vacancy> vacancyPage=new PageImpl<>(vacancies);
        when(vacancyRepository.findAll(pageable)).thenReturn(vacancyPage);
        vacancyService.findAllVacancies(pageable);
        verify(vacancyRepository, times(1)).findAll(pageable);
    }

    @Test
    void testThatYouCanMockCountVacancyMethod() {
        Long numberOfVacancy=2L;
        when(vacancyRepository.count()).thenReturn(numberOfVacancy);
        vacancyService.countVacancy();
        verify(vacancyRepository, times(1)).count();
    }

//    @Test
//    void testThatYouCanMockFindVacancyByUser() throws AppUserNotFoundException {
//        String username="Fako";
//        appUser=appUserRepository.findUserByUsername(username)
//                .orElseThrow(()->new AppUserNotFoundException("Username "+username+" Not Found"));
//        List<Vacancy> vacancies=new ArrayList<>();
//        when(vacancyRepository.findVacancyByUser(appUser)).thenReturn(vacancies);
//        vacancyService.findVacancyByUser(appUser, username);
//        verify(vacancyRepository,times(1)).findVacancyByUser(appUser);
//    }

    @Test
    void testThatYouCanMockUpdateVacancyMethod() throws VacancyNotFoundException {
        Long id=2L;
        when(vacancyRepository.findById(id)).thenReturn(Optional.of(vacancy));
        vacancyService.updateVacancy(vacancy, id);
        verify(vacancyRepository, times(1)).save(vacancy);
    }

    @Test
    void testThatYouCanMockDeleteVacancyByIdMethod() throws VacancyNotFoundException {
        Long id=2L;
        doNothing().when(vacancyRepository).deleteById(id);
        vacancyService.deleteVacancyById(id);
        verify(vacancyRepository, times(1)).deleteById(id);
    }

    @Test
    void testThatYouCanMockDeleteAllVacanciesMethod() {
        doNothing().when(vacancyRepository).deleteAll();
        vacancyService.deleteAllVacancies();
        verify(vacancyRepository, times(1)).deleteAll();
    }
}