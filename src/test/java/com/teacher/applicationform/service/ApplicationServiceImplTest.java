package com.teacher.applicationform.service;

import com.teacher.applicationform.exception.ApplicationFormNotFoundException;
import com.teacher.applicationform.model.ApplicationForm;
import com.teacher.applicationform.repository.ApplicationFormRepository;
import com.teacher.appuser.exception.AppUserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
class ApplicationServiceImplTest {
    @Mock
    private ApplicationFormRepository applicationFormRepository;

    @InjectMocks
    private ApplicationFormService applicationFormService=new ApplicationServiceImpl();

    ApplicationForm applicationForm;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        applicationForm=new ApplicationForm();
    }

    @Test
    void testThatYouCanMockSaveApplicationMethod() throws AppUserNotFoundException {
        when(applicationFormRepository.save(applicationForm)).thenReturn(applicationForm);
        applicationFormService.saveApplication(applicationForm);
        ArgumentCaptor<ApplicationForm> applicationFormArgumentCaptor=ArgumentCaptor.forClass(ApplicationForm.class);
        verify(applicationFormRepository, times(1)).save(applicationFormArgumentCaptor.capture());
        ApplicationForm capturedApplication=applicationFormArgumentCaptor.getValue();
        assertEquals(capturedApplication,applicationForm);
    }

    @Test
    void testThatYouCanMockFindApplicationByIdMethod() throws ApplicationFormNotFoundException {
        Long id=1L;
        when(applicationFormRepository.findById(id)).thenReturn(Optional.of(applicationForm));
        applicationFormService.findApplicationById(id);
        verify(applicationFormRepository, times(1)).findById(id);
    }

    @Test
    void testThatYouCanMockFindAllApplicationsMethod() {
        Integer pageNumber=0;
        Pageable pageable= PageRequest.of(pageNumber, 10);
        List<ApplicationForm> applicationFormList=new ArrayList<>();
        Page<ApplicationForm> applicationFormPage=new PageImpl<>(applicationFormList);
        when(applicationFormRepository.findAll(pageable)).thenReturn(applicationFormPage);
        applicationFormService.findAllApplications(pageNumber);
        verify(applicationFormRepository, times(1)).findAll(pageable);
    }

    @Test
    void testThatYouCanMockDeleteApplicationByIdMethod() throws ApplicationFormNotFoundException {
        Long id=2L;
        doNothing().when(applicationFormRepository).deleteById(id);
        applicationFormService.deleteApplicationById(id);
        verify(applicationFormRepository, times(1)).deleteById(id);
    }

    @Test
    void testThatYouCanMockDeleteAllApplicationMethod() {
        doNothing().when(applicationFormRepository).deleteAll();
        applicationFormService.deleteAllApplications();
        verify(applicationFormRepository, times(1)).deleteAll();
    }
}