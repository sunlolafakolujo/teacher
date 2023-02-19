package com.teacher.applicationform.service;

import com.teacher.applicationform.exception.ApplicationFormNotFoundException;
import com.teacher.applicationform.model.ApplicationForm;
import com.teacher.applicationform.repository.ApplicationFormRepository;
import com.teacher.appteacher.model.Teacher;
import com.teacher.appteacher.repository.TeacherRepository;
import com.teacher.appuser.exception.AppUserNotFoundException;
import com.teacher.appuser.model.AppUser;
import com.teacher.appuser.repository.AppUserRepository;
import com.teacher.configuration.jwt.JwtRequestFilter;
import com.teacher.vacancy.exception.VacancyNotFoundException;
import com.teacher.vacancy.model.Vacancy;
import com.teacher.vacancy.repository.VacancyRepository;
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
    @Autowired
    private VacancyRepository vacancyRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public ApplicationForm saveApplication(ApplicationForm applicationForm) throws AppUserNotFoundException {
        String searchKey= JwtRequestFilter.CURRENT_USER;
        AppUser appUser=appUserRepository.findByUsernameOrEmailOrMobile(searchKey,searchKey,searchKey,searchKey)
                .orElseThrow(()->new AppUserNotFoundException("User "+searchKey+" Not Found"));
        Teacher teacher=teacherRepository.findTeacherByEmailOrUsernameOrPhoneOrUserId(appUser);
        applicationForm.setTeacher(teacher);
        return applicationFormRepository.save(applicationForm);
    }

    @Override
    public ApplicationForm findApplicationById(Long id) throws ApplicationFormNotFoundException {
        return applicationFormRepository.findById(id)
                .orElseThrow(()->new ApplicationFormNotFoundException("Form ID "+id+" Not Found"));
    }

    @Override
    public ApplicationForm findByVacancy(String vacancyCode) throws VacancyNotFoundException {
        Vacancy vacancy=vacancyRepository.findByJobCode(vacancyCode)
                .orElseThrow(()->new VacancyNotFoundException("Vacancy code "+vacancyCode+" Not Found"));
        return applicationFormRepository.findByVacancy(vacancy);
    }

    @Override
    public List<ApplicationForm> findAllApplications(Integer pageNumber) {
        Pageable pageable= PageRequest.of(pageNumber, 10);
        Page<ApplicationForm> applicationForms=applicationFormRepository.findAll(pageable);
        return applicationForms.toList();
    }

    @Override
    public void deleteApplicationById(Long id) throws ApplicationFormNotFoundException {
        if (applicationFormRepository.existsById(id)){
            applicationFormRepository.deleteById(id);
        }else {
            throw new ApplicationFormNotFoundException("Application ID "+id+" Not Found");
        }
    }

    @Override
    public void deleteAllApplications() {
        applicationFormRepository.deleteAll();
    }
}
