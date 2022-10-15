package com.teacher.vacancy.service;

import com.teacher.vacancy.exception.VacancyNotFoundException;
import com.teacher.vacancy.model.Vacancy;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VacancyService {
    Vacancy saveVacancy(Vacancy vacancy);
    Vacancy findVacancyById(Long id) throws VacancyNotFoundException;
//    Vacancy findVacancyByJobId(String jobId) throws VacancyNotFoundException;
    List<Vacancy> findVacancyByJobTitle(String jobTitle,Pageable pageable) throws VacancyNotFoundException;
    List<Vacancy> findAllVacancies(Pageable pageable);
    Long countVacancy();
    Vacancy updateVacancy(Vacancy vacancy, Long id) throws VacancyNotFoundException;
    void deleteVacancyById(Long id) throws VacancyNotFoundException;
    void deleteAllVacancies();
}
