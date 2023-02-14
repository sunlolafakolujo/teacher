package com.teacher.school.repository;

import com.teacher.school.model.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School,Long>,SchoolRepositoryCustom {
}
