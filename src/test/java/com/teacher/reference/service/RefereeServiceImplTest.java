package com.teacher.reference.service;

import com.teacher.reference.repository.RefereeRepository;
import com.teacher.reference.exception.ReferenceNotFoundException;
import com.teacher.reference.model.Referee;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
class RefereeServiceImplTest {

    @Mock
    private RefereeRepository refereeRepository;

    @InjectMocks
    private ReferenceService referenceService=new RefereeServiceImpl();

    Referee referee;

    @BeforeEach
    void setUp() {
        referee=new Referee();
    }

    @Test
    void testThatYouCanSaveRefereeMethod() {
        Mockito.when(refereeRepository.save(referee)).thenReturn(referee);

        referenceService.saveReferee(referee);

        ArgumentCaptor<Referee> argumentCaptor=ArgumentCaptor.forClass(Referee.class);

        Mockito.verify(refereeRepository, Mockito.times(1)).save(argumentCaptor.capture());

        Referee capturedReference= argumentCaptor.getValue();

        assertEquals(capturedReference, referee);
    }

    @Test
    void testThatYouCanMockFindRefereeByIdMethod() throws ReferenceNotFoundException {
        Long id=2L;
        Mockito.when(refereeRepository.findById(id)).thenReturn(Optional.of(referee));

        referenceService.findRefereeById(id);

        Mockito.verify(refereeRepository, Mockito.times(1)).findById(id);
    }

    @Test
    void testThatYouCaMockFindAllRefereesMethod() {

        Integer pageNumber=0;
        Integer pageSize=10;

        List<Referee> refereeList=new ArrayList<>();

        Page<Referee> refereePage=new PageImpl<>(refereeList);

        Pageable pageable= PageRequest.of(pageNumber, pageSize);

        Mockito.when(refereeRepository.findAll(pageable)).thenReturn(refereePage);

        referenceService.findAllReferees(pageable);

        Mockito.verify(refereeRepository, Mockito.times(1)).findAll(pageable);
    }

    @Test
    void testThatYouCanUpdateRefereeMethod() throws ReferenceNotFoundException {
        Long id=1L;
        Mockito.when(refereeRepository.findById(id)).thenReturn(Optional.of(referee));

        referenceService.updateReferee(referee, id);

        Mockito.verify(refereeRepository, Mockito.times(1)).save(referee);
    }

    @Test
    void testThatYouCanMockDeleteRefereeByIdMethod() throws ReferenceNotFoundException {

        Long id=2L;
        doNothing().when(refereeRepository).deleteById(id);

        referenceService.deleteRefereeById(id);

        verify(refereeRepository, times(1)).deleteById(id);
    }

    @Test
    void testThatYouCanMockDeleteAllReferees() {

        doNothing().when(refereeRepository).deleteAll();

        referenceService.deleteAllReferees();

        verify(refereeRepository, times(1)).deleteAll();
    }
}