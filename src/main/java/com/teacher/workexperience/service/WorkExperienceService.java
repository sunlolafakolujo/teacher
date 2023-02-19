package com.teacher.workexperience.service;

import com.teacher.workexperience.exception.WorkExperienceNotFoundException;
import com.teacher.workexperience.model.WorkExperience;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WorkExperienceService {
    WorkExperience saveWorkExperience(WorkExperience saveWorkExperience);
    WorkExperience findWorkExperienceById(Long id) throws WorkExperienceNotFoundException;
    List<WorkExperience> findAllWorkExperience(Integer pageNumber);
    void deleteWorkExperienceById(Long id) throws WorkExperienceNotFoundException;
    void deleteAllWorkExperience();
}
