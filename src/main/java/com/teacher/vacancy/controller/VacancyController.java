package com.teacher.vacancy.controller;

import com.teacher.appuser.exception.AppUserNotFoundException;
import com.teacher.appuser.service.AppUserService;
import com.teacher.vacancy.exception.VacancyNotFoundException;
import com.teacher.vacancy.model.*;
import com.teacher.vacancy.service.VacancyService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/teacher")
@AllArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;
    private final ModelMapper modelMapper;
    private final AppUserService appUserService;

    @PostMapping("/saveVacancyByUsername")
    public ResponseEntity<NewVacancy> createVacancy(@Valid @RequestBody NewVacancy newVacancy)
                                                    throws AppUserNotFoundException {
        Vacancy vacancy=modelMapper.map(newVacancy, Vacancy.class);
        Vacancy post=vacancyService.saveVacancy(vacancy);
        NewVacancy posted=modelMapper.map(post, NewVacancy.class);
        return new ResponseEntity<>(posted, HttpStatus.CREATED);
    }

    @GetMapping("/findVacancyById")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<VacancyDtos> getVacancyById(@RequestParam("id") Long id) throws VacancyNotFoundException {
        Vacancy vacancy=vacancyService.findVacancyById(id);
        VacancyDtos vacancyDtos=convertVacancyToDtos(vacancy);
        return new ResponseEntity<>(vacancyDtos, HttpStatus.OK);
    }

    @GetMapping("/findVacancyByJobTitle")
    public ResponseEntity<List<VacancyDtos>> getVacancyByJobId(@RequestParam("jobTitle") String jobTitle,
                                                               @RequestParam(defaultValue = "0")Integer pageNumber)
                                                                                    throws VacancyNotFoundException {
        return new ResponseEntity<>(vacancyService.findVacancyByJobTitle(jobTitle, pageNumber)
                .stream()
                .map(this::convertVacancyToDtos)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/findAllVacanciesOrByUser")
    @PreAuthorize("hasAnyRole('PARENT','SCHOOL')")
    public ResponseEntity<List<VacancyDto>> getAllVacancies(@RequestParam(defaultValue = "0")Integer pageNumber,
                                                            @RequestParam(defaultValue = "")String searchKey)
                                                                                throws AppUserNotFoundException {
        return new ResponseEntity<>(vacancyService.findAllVacanciesOrByEmailOrMobileOrUsernameOrUserId(searchKey, pageNumber)
                .stream()
                .map(this::convertVacancyToDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @PutMapping("/updateVacancy")
    @PreAuthorize("hasAnyRole('PARENT','SCHOOL')")
    public ResponseEntity<UpdateVacancy> updateVacancy(@RequestBody UpdateVacancy updateVacancy,
                                                       @RequestParam("id") Long id) throws VacancyNotFoundException {
        Vacancy vacancy=modelMapper.map(updateVacancy, Vacancy.class);
        Vacancy post=vacancyService.updateVacancy(vacancy, id);
        UpdateVacancy posted=modelMapper.map(post, UpdateVacancy.class);
        return new ResponseEntity<>(posted, HttpStatus.OK);
    }

    @DeleteMapping("/deleteUserVacancyByCode")
    public ResponseEntity<?> deleteUserVacancyById(@RequestParam("id")String jobCode) throws AppUserNotFoundException {
        vacancyService.deleteUserVacancyById(jobCode);
        return ResponseEntity.ok().body("Vacancy code "+jobCode+" has being deleted");
    }

    @DeleteMapping("/deleteUserVacancies")
    public ResponseEntity<?> deleteUserVacancies(@RequestParam(defaultValue = "0")Integer pageNumber) throws AppUserNotFoundException {
        vacancyService.deleteAllUserVacancies(pageNumber);
        return ResponseEntity.ok().body("User vacancies has being deleted");
    }

    @DeleteMapping("/deleteVacancyById")
    public ResponseEntity<?> deleteVacancyById(@RequestParam("id") Long id) throws VacancyNotFoundException {
        vacancyService.deleteVacancyById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteAllVacancies")
    public ResponseEntity<?> deleteAllVacancies(){
        vacancyService.deleteAllVacancies();
        return ResponseEntity.noContent().build();
    }


    private VacancyDto convertVacancyToDto(Vacancy vacancy){
        VacancyDto vacancyDto=new VacancyDto();

        vacancyDto.setJobDetail("Recruiter: "+vacancy.getAppUser().getUsername()+
                "\n"+"Published Date: "+vacancy.getPublishedDate()+
                "\n"+"Closing Date: "+vacancy.getClosingDate()+
                "\n"+"JobCode:"+vacancy.getJobCode()+
                "\n"+"Job Title: "+vacancy.getJobTitle()+
                "\n"+"About Us: "+vacancy.getAboutUs()+
                "\n"+"Job Type: "+vacancy.getJobType()+
                "\n"+"Job Location: "+vacancy.getJobLocation()+
                "\n"+"Job Schedule: "+vacancy.getJobSchedule()+
                "\n"+"Qualification: "+vacancy.getQualification()+
                "\n"+"Skills: "+vacancy.getSkillRequirement() +
                "\n"+"Key Responsibility: "+vacancy.getKeyResponsibility()+
                "\n"+"Benefits: "+vacancy.getBenefit());
        return vacancyDto;
    }

    private VacancyDtos convertVacancyToDtos(Vacancy vacancy){
        VacancyDtos vacancyDto=new VacancyDtos();
        vacancyDto.setRecruiterName(vacancy.getAppUser().getUsername());
        vacancyDto.setAboutUs(vacancy.getAboutUs());
        vacancyDto.setClosingDate(vacancy.getClosingDate());
        vacancyDto.setPublishedDate(vacancy.getPublishedDate());
        vacancyDto.setJobLocation(vacancy.getJobLocation());
        vacancyDto.setJobTitle(vacancy.getJobTitle());
        vacancyDto.setJobSchedule(vacancy.getJobSchedule());
        vacancyDto.setJobType(vacancy.getJobType());
        vacancyDto.setQualification(vacancy.getQualification());
        vacancyDto.setKeyResponsibility(vacancy.getKeyResponsibility());
        vacancyDto.setSkillRequirement(vacancy.getSkillRequirement());
        return vacancyDto;

    }


}
