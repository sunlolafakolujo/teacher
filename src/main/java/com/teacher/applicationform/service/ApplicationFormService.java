package com.teacher.applicationform.service;

import com.teacher.applicationform.exception.ApplicationFormNotFoundException;
import com.teacher.applicationform.model.ApplicationForm;
import com.teacher.appuser.exception.AppUserNotFoundException;
import com.teacher.vacancy.exception.VacancyNotFoundException;
import com.teacher.vacancy.model.Vacancy;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface
ApplicationFormService {
    ApplicationForm saveApplication(ApplicationForm applicationForm) throws AppUserNotFoundException;
    ApplicationForm findApplicationById(Long id) throws ApplicationFormNotFoundException;
    ApplicationForm findByVacancy(String vacancyCode) throws VacancyNotFoundException;
    List<ApplicationForm> findAllApplications(Integer pageNumber);
    void deleteApplicationById(Long id) throws ApplicationFormNotFoundException;
    void deleteAllApplications();
}
