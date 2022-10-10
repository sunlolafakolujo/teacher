package com.teacher.vacancy.service;

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

    @Override
    public Vacancy saveVacancy(Vacancy vacancy) throws VacancyNotFoundException {
        Vacancy jobId=vacancyRepository.findByJobId(vacancy.getJobId());
        if (jobId!=null){
            throw new VacancyNotFoundException("Vacancy already exist");
        }
        vacancy.setJobDetails(":\nCompany:"+vacancy.getCompanyName()+
                                "\n\nJobId:"+vacancy.getJobId()+
                                "\n\nJob Title:"+vacancy.getJobTitle()+
                                "\n\nEducation:\n"+vacancy.getExperienceEducation()+
                                "\n\nSkills:\n"+vacancy.getSkillRequirement() +
                                "\n\nKey Responsibility:\n"+vacancy.getKeyResponsibility()+
                                "\n\nBenefits:\n"+vacancy.getBenefit());

        return vacancyRepository.save(vacancy);
    }

    @Override
    public Vacancy findVacancyById(Long id) throws VacancyNotFoundException {
        Vacancy vacancy=vacancyRepository.findById(id)
                .orElseThrow(()->new VacancyNotFoundException("Vacancy Not Found"));
        return vacancy;
    }

    @Override
    public Vacancy findVacancyByJobId(String jobId) throws VacancyNotFoundException {
        Vacancy vacancy=vacancyRepository.findByJobId(jobId);
        if (vacancy==null){
            throw new VacancyNotFoundException("Vacancy Not Found");
        }
        return vacancy;
    }

    @Override
    public List<Vacancy> findVacancyByJobTitle(String jobTitle, Pageable pageable) throws VacancyNotFoundException {
        pageable= PageRequest.of(0, 10);
        List<Vacancy> vacancies=vacancyRepository.findByJobTitle(jobTitle,pageable);
        if (vacancies.isEmpty()){
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
    public Vacancy updateVacancy(Vacancy vacancy, Long id) throws VacancyNotFoundException {
        Vacancy savedVacancy=vacancyRepository.findById(id)
                .orElseThrow(()->new VacancyNotFoundException("Vacancy Not found"));
        if (Objects.nonNull(vacancy.getBenefit()) && !"".equalsIgnoreCase(vacancy.getBenefit())){
            savedVacancy.setBenefit(vacancy.getBenefit());
        }if (Objects.nonNull(vacancy.getExperienceEducation()) && !"".equalsIgnoreCase(vacancy.getExperienceEducation())){
            savedVacancy.setExperienceEducation(vacancy.getExperienceEducation());
        }if (Objects.nonNull(vacancy.getKeyResponsibility()) && !"".equalsIgnoreCase(vacancy.getKeyResponsibility())){
            savedVacancy.setKeyResponsibility(vacancy.getKeyResponsibility());
        }if (Objects.nonNull(vacancy.getSkillRequirement()) && !"".equalsIgnoreCase(vacancy.getSkillRequirement())){
            savedVacancy.setSkillRequirement(vacancy.getSkillRequirement());
        }if (Objects.nonNull(vacancy.getClosingDate())){
            savedVacancy.setClosingDate(vacancy.getClosingDate());
        }if (Objects.nonNull(vacancy.getJobTitle()) && !"".equalsIgnoreCase(vacancy.getJobTitle())){
            savedVacancy.setJobTitle(vacancy.getJobTitle());
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
