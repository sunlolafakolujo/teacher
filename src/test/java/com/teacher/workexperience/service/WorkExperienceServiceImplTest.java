package com.teacher.workexperience.service;

import com.teacher.workexperience.dao.WorkExperienceRepository;
import com.teacher.workexperience.exception.WorkExperienceNotFoundException;
import com.teacher.workexperience.model.WorkExperience;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
class WorkExperienceServiceImplTest {

    @Mock
    private WorkExperienceRepository workExperienceRepository;

    @InjectMocks
    WorkExperienceService workExperienceService=new WorkExperienceServiceImpl();

    WorkExperience workExperience;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        workExperience=new WorkExperience();
    }

    @Test
    void testThatYouCanMockSaveWorkExperienceMethod() {
        when(workExperienceRepository.save(workExperience)).thenReturn(workExperience);

        workExperienceService.saveWorkExperience(workExperience);

        ArgumentCaptor<WorkExperience> argumentCaptor=ArgumentCaptor.forClass(WorkExperience.class);

        Mockito.verify(workExperienceRepository, Mockito.times(1)).save(argumentCaptor.capture());

        WorkExperience capturedWorkExperience=argumentCaptor.getValue();

        assertEquals(capturedWorkExperience, workExperience);
    }

    @Test
    void testThatYoUCanMockFindWorkExperienceByIdMethod() throws WorkExperienceNotFoundException {
        Long id=2L;
        when(workExperienceRepository.findById(id)).thenReturn(Optional.of(workExperience));

        workExperienceService.findWorkExperienceById(id);

        Mockito.verify(workExperienceRepository, Mockito.times(1)).findById(id);
    }

    @Test
    void testThatYouCanMockFindAllWorkExperienceMethod() {
        List<WorkExperience> workExperiences=new ArrayList<>();

        Page<WorkExperience> experiencePage=new PageImpl<>(workExperiences);

        Pageable pageable= PageRequest.of(0, 10);

        when(workExperienceRepository.findAll(pageable)).thenReturn(experiencePage);

        workExperienceService.findAllWorkExperience(pageable);

        Mockito.verify(workExperienceRepository, Mockito.times(1)).findAll(pageable);
    }

    @Test
    void testThatYouCanMockDeleteWorkExperienceByIdMethod() throws WorkExperienceNotFoundException {
        Long id=2L;
        doNothing().when(workExperienceRepository).deleteById(id);

        workExperienceService.deleteWorkExperienceById(id);

        verify(workExperienceRepository, times(1)).deleteById(id);
    }

    @Test
    void testThatYouCanDeleteAllWorkExperienceMethod() {

        doNothing().when(workExperienceRepository).deleteAll();

        workExperienceService.deleteAllWorkExperience();

        verify(workExperienceRepository, times(1)).deleteAll();
    }
}