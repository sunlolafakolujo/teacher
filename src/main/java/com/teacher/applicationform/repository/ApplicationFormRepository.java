package com.teacher.applicationform.repository;

import com.teacher.applicationform.model.ApplicationForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationFormRepository extends JpaRepository<ApplicationForm,Long> {

}
