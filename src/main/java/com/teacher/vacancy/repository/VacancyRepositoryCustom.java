package com.teacher.vacancy.repository;

import com.teacher.appuser.model.AppUser;
import com.teacher.vacancy.model.Vacancy;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VacancyRepositoryCustom {
    @Query("From Vacancy v Where v.jobCode=?1")
    Optional<Vacancy> findByJobCode(String jobCode);

    @Query("From Vacancy v Where v.jobTitle=?1")
    List<Vacancy> findByJobTitle(String key1, Pageable pageable);

    @Query("From Vacancy v where v.appUser=?1")
    List<Vacancy> findByEmailOrUsernameOrMobileOrUserId(AppUser appUser,Pageable pageable);

    @Modifying
    @Query("DELETE FROM Vacancy v WHERE v.jobCode=?1 AND v.appUser=?2")
    void deleteUserVacancyById(String jobCode, AppUser appUser);
}
