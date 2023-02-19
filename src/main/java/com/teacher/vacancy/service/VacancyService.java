package com.teacher.vacancy.service;

import com.teacher.appuser.exception.AppUserNotFoundException;
import com.teacher.appuser.model.AppUser;
import com.teacher.vacancy.exception.VacancyNotFoundException;
import com.teacher.vacancy.model.Vacancy;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VacancyService {
    Vacancy saveVacancy(Vacancy vacancy) throws AppUserNotFoundException;
    Vacancy findVacancyById(Long id) throws VacancyNotFoundException;
    Vacancy findVacancyByJobCode(String jobCode) throws VacancyNotFoundException;
    List<Vacancy> findVacancyByJobTitle(String jobTitle,Integer pageNumber) throws VacancyNotFoundException;
    List<Vacancy> findAllVacanciesOrByEmailOrMobileOrUsernameOrUserId(String searchKey,Integer pageNumber) throws AppUserNotFoundException;
    Long countVacancy();
    Vacancy updateVacancy(Vacancy vacancy, Long id) throws VacancyNotFoundException;
    void deleteAllUserVacancies(Integer pageNumber) throws AppUserNotFoundException;
    void deleteUserVacancyById(String jobCode) throws AppUserNotFoundException;
    void deleteVacancyById(Long id) throws VacancyNotFoundException;
    void deleteAllVacancies();
}
