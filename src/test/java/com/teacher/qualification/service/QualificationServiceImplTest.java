package com.teacher.qualification.service;

import com.teacher.qualification.repository.QualificationRepository;
import com.teacher.qualification.exception.QualificationNotFoundException;
import com.teacher.qualification.model.Qualification;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Sql(scripts = "classpath:db/insert.sql")
@Slf4j
class QualificationServiceImplTest {
    @Mock
    private QualificationRepository qualificationRepository;
    @InjectMocks
    QualificationService  qualificationService=new QualificationServiceImpl();
    Qualification qualification;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        qualification=new Qualification();
    }

    @Test
    void testThatYouCanMockSaveQualificationMethod() {
        Mockito.when(qualificationRepository.save(qualification)).thenReturn(qualification);
        qualificationService.saveQualification(qualification);
        ArgumentCaptor<Qualification> qualificationArgumentCaptor=ArgumentCaptor.forClass(Qualification.class);
        Mockito.verify(qualificationRepository, Mockito.times(1))
                .save(qualificationArgumentCaptor.capture());
        Qualification captureQualification=qualificationArgumentCaptor.getValue();
        assertEquals(captureQualification, qualification);

    }

    @Test
    void testThatYouCanMockFindQualificationByIdMethod() throws QualificationNotFoundException {
        Long id=1L;
        Mockito.when(qualificationRepository.findById(id)).thenReturn(Optional.of(qualification));
        qualificationService.findQualificationById(id);
        Mockito.verify(qualificationRepository, Mockito.times(1)).findById(id);
    }

    @Test
    void testThatYoCanMockFindAllQualificationsMethod() {
        List<Qualification> qualificationList=new ArrayList<>();
        Page<Qualification> qualificationPage=new PageImpl<>(qualificationList);
        Integer pageNumber=0;
        Pageable pageable=PageRequest.of(pageNumber, 10);
        Mockito.when(qualificationRepository.findAll(pageable)).thenReturn(qualificationPage);
        qualificationService.findAllQualifications(pageNumber);
        Mockito.verify(qualificationRepository, Mockito.times(1)).findAll(pageable);
    }

    @Test
    void testThatYouCanMockDeleteQualificationByIdMethod() throws QualificationNotFoundException {
        Long id =1L;
        doNothing().when(qualificationRepository).deleteById(id);
        qualificationService.deleteQualificationById(id);
        verify(qualificationRepository, times(1)).deleteById(id);
    }

    @Test
    void testThatYouCanMockDeleteAllQualificationMethod() {
        doNothing().when(qualificationRepository).deleteAll();
        qualificationService.deleteAllQualification();
        verify(qualificationRepository, times(1)).deleteAll();
    }
}