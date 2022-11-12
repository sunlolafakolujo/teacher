package com.teacher.applicationform.service;

import com.teacher.applicationform.exception.ApplicationFormNotFoundException;
import com.teacher.applicationform.model.ApplicationForm;
import com.teacher.vacancy.model.Vacancy;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ApplicationFormService {
    ApplicationForm saveApplication(ApplicationForm applicationForm);
    ApplicationForm findApplicationById(Long id) throws ApplicationFormNotFoundException;
    List<ApplicationForm> findAllApplications(Pageable pageable);
    void deleteApplicationById(Long id) throws ApplicationFormNotFoundException;
    void deleteAllApplications();
}
