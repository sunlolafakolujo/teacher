package com.teacher.applicationform.repository;

import com.teacher.applicationform.model.ApplicationForm;
import com.teacher.appteacher.model.Teacher;
import com.teacher.vacancy.model.Vacancy;
import org.springframework.data.jpa.repository.Query;

public interface ApplicationFormRepositoryCustom {
    @Query("FROM ApplicationForm a WHERE a.vacancy=?1")
    ApplicationForm findByVacancy(Vacancy vacancy);

    @Query("FROM ApplicationForm a WHERE a.teacher=?1")
    ApplicationForm findByTeacher(Teacher teacher);
}
