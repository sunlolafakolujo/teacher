package com.teacher.applicationform.service;

import com.teacher.applicationform.exception.ApplicationFormNotFoundException;
import com.teacher.applicationform.model.ApplicationForm;
import com.teacher.applicationform.repository.ApplicationFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Transactional
@Service
public class ApplicationServiceImpl implements ApplicationFormService{
    @Autowired
    private ApplicationFormRepository applicationFormRepository;

//    @Autowired
//    private VacancyRepository vacancyRepository;

    @Override
    public ApplicationForm saveApplication(ApplicationForm applicationForm) {
        return applicationFormRepository.save(applicationForm);
    }

    @Override
    public ApplicationForm findApplicationById(Long id) throws ApplicationFormNotFoundException {
        return applicationFormRepository.findById(id)
                .orElseThrow(()->new ApplicationFormNotFoundException("Form ID "+id+" Not Found"));
    }

    @Override
    public List<ApplicationForm> findAllApplications(Pageable pageable) {
        pageable= PageRequest.of(0, 10);
        Page<ApplicationForm> applicationForms=applicationFormRepository.findAll(pageable);
        return applicationForms.toList();
    }

    @Override
    public void deleteApplicationById(Long id) throws ApplicationFormNotFoundException {
        applicationFormRepository.deleteById(id);
        Optional<ApplicationForm> optionalApplicationForm=applicationFormRepository.findById(id);
        if (optionalApplicationForm.isPresent()){
            throw new ApplicationFormNotFoundException("Application ID "+id+" is not deleted");
        }
    }

    @Override
    public void deleteAllApplications() {
        applicationFormRepository.deleteAll();
    }
}
