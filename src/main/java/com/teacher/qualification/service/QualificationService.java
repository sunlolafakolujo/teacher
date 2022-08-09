package com.teacher.qualification.service;

import com.teacher.qualification.exception.QualificationException;
import com.teacher.qualification.model.Qualification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface QualificationService {
    Qualification saveQualification(Qualification qualification);
    Qualification findQualificationById(Long id) throws QualificationException;
    List<Qualification> findAllQualifications(Pageable pageable);
    void deleteQualificationById(Long id) throws QualificationException;
    void deleteAllQualification();

}
