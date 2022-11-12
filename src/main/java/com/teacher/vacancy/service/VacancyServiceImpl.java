package com.teacher.vacancy.service;

import com.teacher.appuser.exception.AppUserNotFoundException;
import com.teacher.appuser.model.AppUser;
import com.teacher.appuser.repository.AppUserRepository;
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
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class VacancyServiceImpl implements VacancyService{
    @Autowired
    private VacancyRepository vacancyRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public Vacancy saveVacancy(Vacancy vacancy) {
        return vacancyRepository.save(vacancy);
    }

    @Override
    public Vacancy findVacancyById(Long id) throws VacancyNotFoundException {
        Vacancy vacancy=vacancyRepository.findById(id)
                .orElseThrow(()->new VacancyNotFoundException("Vacancy Not Found"));
        return vacancy;
    }

    @Override
    public List<Vacancy> findVacancyByJobTitle(String jobTitle, Pageable pageable) throws VacancyNotFoundException {
        pageable= PageRequest.of(0, 10);
        List<Vacancy> vacancies=vacancyRepository.findByJobTitle(jobTitle,pageable);
        if (vacancies==null){
            throw new VacancyNotFoundException("Vacancy Not Found");
        }
        return vacancies;
    }

    @Override
    public List<Vacancy> findAllVacancies(Pageable pageable) {
        pageable=PageRequest.of(0, 10);
        Page<Vacancy> vacancyPage=vacancyRepository.findAll(pageable);
        return vacancyPage.toList();
    }

    @Override
    public Long countVacancy() {
        return vacancyRepository.count();
    }

    @Override
    public List<Vacancy> findVacancyByUser(AppUser appUser, String username) throws AppUserNotFoundException {
        appUser=appUserRepository.findUserByUsername(username)
                .orElseThrow(()-> new AppUserNotFoundException("Username "+username+" Not Found"));
        return vacancyRepository.findVacancyByUser(appUser);
    }

    @Override
    public Vacancy updateVacancy(Vacancy vacancy, Long id) throws VacancyNotFoundException {
        Vacancy savedVacancy=vacancyRepository.findById(id)
                .orElseThrow(()->new VacancyNotFoundException("Vacancy Not found"));
        if (Objects.nonNull(vacancy.getBenefit()) && !"".equalsIgnoreCase(vacancy.getBenefit())){
            savedVacancy.setBenefit(vacancy.getBenefit());
        }if (Objects.nonNull(vacancy.getQualification()) && !"".equalsIgnoreCase(vacancy.getQualification())){
            savedVacancy.setQualification(vacancy.getQualification());
        }if (Objects.nonNull(vacancy.getKeyResponsibility()) && !"".equalsIgnoreCase(vacancy.getKeyResponsibility())){
            savedVacancy.setKeyResponsibility(vacancy.getKeyResponsibility());
        }if (Objects.nonNull(vacancy.getSkillRequirement()) && !"".equalsIgnoreCase(vacancy.getSkillRequirement())){
            savedVacancy.setSkillRequirement(vacancy.getSkillRequirement());
        }if (Objects.nonNull(vacancy.getClosingDate())){
            savedVacancy.setClosingDate(vacancy.getClosingDate());
        }if (Objects.nonNull(vacancy.getAboutUs()) && !"".equalsIgnoreCase(vacancy.getAboutUs())){
            savedVacancy.setAboutUs(vacancy.getAboutUs());
        }if (Objects.nonNull(vacancy.getJobType())){
            savedVacancy.setJobType(vacancy.getJobType());
        }if (Objects.nonNull(vacancy.getJobSchedule()) && !"".equalsIgnoreCase(vacancy.getJobSchedule())){
            savedVacancy.setJobSchedule(vacancy.getJobSchedule());
        }if (Objects.nonNull(vacancy.getJobTitle()) && !"".equalsIgnoreCase(vacancy.getJobTitle())){
            savedVacancy.setJobTitle(vacancy.getJobTitle());
        }if (Objects.nonNull(vacancy.getJobLocation()) && !"".equalsIgnoreCase(vacancy.getJobLocation())){
            savedVacancy.setJobLocation(vacancy.getJobLocation());
        }if (Objects.nonNull(vacancy.getMessageToApplicant()) && !"".equalsIgnoreCase(vacancy.getMessageToApplicant())){
            savedVacancy.setMessageToApplicant(vacancy.getMessageToApplicant());
        }
        return vacancyRepository.save(savedVacancy);
    }

    @Override
    public void deleteVacancyById(Long id) throws VacancyNotFoundException {
        vacancyRepository.deleteById(id);
        Optional<Vacancy> optionalVacancy=vacancyRepository.findById(id);
        if (optionalVacancy.isPresent()){
            throw new VacancyNotFoundException("Vacancy Not Deleted");
        }
    }

    @Override
    public void deleteAllVacancies() {
        vacancyRepository.deleteAll();
    }
}
