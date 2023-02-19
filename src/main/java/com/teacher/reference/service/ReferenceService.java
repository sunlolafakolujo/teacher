package com.teacher.reference.service;

import com.teacher.reference.exception.ReferenceNotFoundException;
import com.teacher.reference.model.Referee;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReferenceService {

    Referee saveReferee(Referee referee);
    Referee findRefereeById(Long id) throws ReferenceNotFoundException;
    List<Referee> findAllReferees(Integer pageNumber);
    Referee updateReferee(Referee referee, Long id) throws ReferenceNotFoundException;
    void deleteRefereeById(Long id) throws ReferenceNotFoundException;
    void deleteAllReferees();
}
