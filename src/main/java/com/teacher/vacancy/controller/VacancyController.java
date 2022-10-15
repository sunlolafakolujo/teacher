package com.teacher.vacancy.controller;

import com.teacher.vacancy.exception.VacancyNotFoundException;
import com.teacher.vacancy.model.*;
import com.teacher.vacancy.service.VacancyService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/teacher/vacancy")
@AllArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;
    private final ModelMapper modelMapper;

    @PostMapping("/saveVacancy")
    public ResponseEntity<NewVacancy> createVacancy(@Valid @RequestBody NewVacancy newVacancy)  {
        Vacancy vacancy=modelMapper.map(newVacancy, Vacancy.class);
        Vacancy post=vacancyService.saveVacancy(vacancy);
        NewVacancy posted=modelMapper.map(post, NewVacancy.class);
        return new ResponseEntity<>(posted, HttpStatus.CREATED);
    }

    @GetMapping("/findVacancyById/{id}")
    public ResponseEntity<VacancyDtos> getVacancyById(@PathVariable(value = "id") Long id) throws VacancyNotFoundException {
        Vacancy vacancy=vacancyService.findVacancyById(id);
        VacancyDtos vacancyDtos=convertVacancyToDtos(vacancy);
        return new ResponseEntity<>(vacancyDtos, HttpStatus.OK);
    }

    @GetMapping("/findVacancyByJobTitle/{jobTitle}")
    public ResponseEntity<List<VacancyDtos>> getVacancyByJobId(@PathVariable(value = "jobTitle") String jobTitle)
                                                                throws VacancyNotFoundException {
        Pageable pageable=PageRequest.of(0, 10);
        return new ResponseEntity<>(vacancyService.findVacancyByJobTitle(jobTitle, pageable)
                .stream()
                .map(this::convertVacancyToDtos)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/findAllVacancies")
    public ResponseEntity<List<VacancyDto>> getAllVacancies(){
        Pageable  pageable= PageRequest.of(0, 10);
        return new ResponseEntity<>(vacancyService.findAllVacancies(pageable)
                .stream()
                .map(this::convertVacancyToDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @PutMapping("/updateVacancy/{id}")
    public ResponseEntity<UpdateVacancy> updateVacancy(@RequestBody UpdateVacancy updateVacancy,
                                                       @PathVariable(value = "id") Long id) throws VacancyNotFoundException {
        Vacancy vacancy=modelMapper.map(updateVacancy, Vacancy.class);
        Vacancy post=vacancyService.updateVacancy(vacancy, id);
        UpdateVacancy posted=modelMapper.map(post, UpdateVacancy.class);
        return new ResponseEntity<>(posted, HttpStatus.OK);
    }

    @DeleteMapping("/deleteVacancyById/{id}")
    public ResponseEntity<?> deleteVacancyById(@PathVariable(value = "id") Long id) throws VacancyNotFoundException {
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

        vacancyDto.setJobDetail("Company: "+vacancy.getCompanyName()+
                ", "+"Published Date: "+vacancy.getPublishedDate()+
                ", "+"Closing Date: "+vacancy.getClosingDate()+
//                ", "+"JobId:"+vacancy.getJobId()+
                ", "+"Job Title: "+vacancy.getJobTitle()+
                ", "+"About Us: "+vacancy.getAboutUs()+
                ", "+"Job Type: "+vacancy.getJobType()+
                ", "+"Job Location: "+vacancy.getJobLocation()+
                ", "+"Job Schedule: "+vacancy.getJobSchedule()+
                ", "+"Qualification: "+vacancy.getQualification()+
                ", "+"Skills: "+vacancy.getSkillRequirement() +
                ", "+"Key Responsibility: "+vacancy.getKeyResponsibility()+
                ", "+"Benefits: "+vacancy.getBenefit());

        return vacancyDto;
    }

    private VacancyDtos convertVacancyToDtos(Vacancy vacancy){
        VacancyDtos vacancyDto=new VacancyDtos();
        vacancyDto.setCompanyName(vacancy.getCompanyName());
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
