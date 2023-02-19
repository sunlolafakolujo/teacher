package com.teacher.qualification.service;

import com.teacher.qualification.exception.QualificationNotFoundException;
import com.teacher.qualification.model.Qualification;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface QualificationService {
    Qualification saveQualification(Qualification qualification);
    Qualification findQualificationById(Long id) throws QualificationNotFoundException;
    List<Qualification> findAllQualifications(Integer pageNumber);
    void deleteQualificationById(Long id) throws QualificationNotFoundException;
    void deleteAllQualification();

}
