package com.teacher.vacancy.repository;

import com.teacher.vacancy.model.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy,Long> {
}
