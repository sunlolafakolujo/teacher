package com.teacher.workexperience.service;

import com.teacher.workexperience.dao.WorkExperienceRepository;
import com.teacher.workexperience.exception.WorkExperienceNotFoundException;
import com.teacher.workexperience.model.WorkExperience;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkExperienceServiceImpl implements WorkExperienceService{

    @Autowired
    private WorkExperienceRepository workExperienceRepository;

    @Override
    public WorkExperience saveWorkExperience(WorkExperience saveWorkExperience) {

        return workExperienceRepository.save(saveWorkExperience);
    }

    @Override
    public WorkExperience findWorkExperienceById(Long id) throws WorkExperienceNotFoundException {
        WorkExperience workExperience=workExperienceRepository.findById(id)
                .orElseThrow(()->new WorkExperienceNotFoundException("Work Experience id "+id+" Not Found"));

        return workExperience;
    }

    @Override
    public List<WorkExperience> findAllWorkExperience(Pageable pageable) {
        pageable=PageRequest.of(0,10);

        Page<WorkExperience> workExperiences= workExperienceRepository.findAll(pageable);

        return workExperiences.toList();
    }

    @Override
    public void deleteWorkExperienceById(Long id) throws WorkExperienceNotFoundException {

        workExperienceRepository.deleteById(id);

        Optional<WorkExperience> optionalWorkExperience=workExperienceRepository.findById(id);

        if (optionalWorkExperience.isPresent()){
            throw new WorkExperienceNotFoundException("Work Experience "+id+" is not deleted");
        }
    }

    @Override
    public void deleteAllWorkExperience() {

        workExperienceRepository.deleteAll();
    }
}
