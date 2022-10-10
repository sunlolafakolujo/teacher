package com.teacher.vacancy.repository;

import com.teacher.vacancy.model.Vacancy;
import org.springframework.data.jpa.repository.Query;

public interface VacancyRepositoryCustom {
    @Query("From Vacancy v Where v.jobId=?1")
    Vacancy findByJobId(String jobId);

    @Query("From Vacancy v Where v.jobTitle=?1")
    Vacancy findByJobTitle(String jobTitle);
}
