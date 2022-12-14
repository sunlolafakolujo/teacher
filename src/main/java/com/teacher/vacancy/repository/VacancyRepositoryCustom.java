package com.teacher.vacancy.repository;

import com.teacher.appuser.model.AppUser;
import com.teacher.vacancy.model.Vacancy;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VacancyRepositoryCustom {
//    @Query("From Vacancy v Where v.jobId=?1")
//    Vacancy findByJobId(String jobId);

    @Query("From Vacancy v Where v.jobTitle=?1")
    List<Vacancy> findByJobTitle(String jobTitle, Pageable pageable);

    @Query("From Vacancy v where v.appUser=?1")
    List<Vacancy> findVacancyByUser(AppUser appUser);
}
