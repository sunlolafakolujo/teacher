package com.teacher.workexperience.repository;

import com.teacher.workexperience.model.WorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface WorkExperienceRepository extends JpaRepository<WorkExperience, Long> {
}
