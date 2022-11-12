package com.teacher.applicationform.controller;

import com.teacher.applicationform.exception.ApplicationFormNotFoundException;
import com.teacher.applicationform.model.ApplicationForm;
import com.teacher.applicationform.model.ApplicationFormDto;
import com.teacher.applicationform.model.NewApplicationForm;
import com.teacher.applicationform.service.ApplicationFormService;
import com.teacher.event.ApplicationFormEvent;
import com.teacher.vacancy.model.Vacancy;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/teacher/applicationForm")
@AllArgsConstructor
public class ApplicationFormController {
    private final ApplicationFormService applicationFormService;
    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher publisher;

    @PostMapping("/saveApplication")
    public ResponseEntity<NewApplicationForm> saveApplication(@RequestBody @Valid NewApplicationForm newApplicationForm){

//        newApplicationForm.setApplicationId("APP-".concat(UUID.randomUUID().toString()));
//        newApplicationForm.setJobTitle(newApplicationForm.getVacancy().getJobTitle());
        ApplicationForm applicationForm=modelMapper.map(newApplicationForm, ApplicationForm.class);
        ApplicationForm post=applicationFormService.saveApplication(applicationForm);
        publisher.publishEvent(new ApplicationFormEvent(post));
        NewApplicationForm posted=modelMapper.map(post, NewApplicationForm.class);
        return new ResponseEntity<>(posted, HttpStatus.CREATED);
    }

    @GetMapping("/findApplicationById/{id}")
    public ResponseEntity<ApplicationFormDto> getApplicationById(@PathVariable(value = "id") Long id) throws ApplicationFormNotFoundException {
        ApplicationForm applicationForm=applicationFormService.findApplicationById(id);
        ApplicationFormDto applicationFormDto=convertApplicationFormToDto(applicationForm);
        return new ResponseEntity<>(applicationFormDto, HttpStatus.OK);
    }

    @GetMapping("/findAllApplications")
    public ResponseEntity<List<ApplicationFormDto>> getAllApplications(){
        Pageable pageable= PageRequest.of(0, 10);
        return new ResponseEntity<>(applicationFormService
                .findAllApplications(pageable)
                .stream()
                .map(this::convertApplicationFormToDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @DeleteMapping("/deleteApplicationById/{id}")
    public ResponseEntity<?> deleteApplicationById(Long id) throws ApplicationFormNotFoundException {
        applicationFormService.deleteApplicationById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteAllApplications")
    public ResponseEntity<?> deleteAllApplications() {
        applicationFormService.deleteAllApplications();
        return ResponseEntity.noContent().build();
    }



    private ApplicationFormDto convertApplicationFormToDto(ApplicationForm applicationForm){
        ApplicationFormDto applicationFormDto=new ApplicationFormDto();

//        applicationFormDto.setApplicationId(applicationForm.getApplicationId());
        applicationFormDto.setFirstName(applicationForm.getFirstName());
        applicationFormDto.setLastName(applicationForm.getLastName());
        applicationFormDto.setEmail(applicationForm.getEmail());
        applicationFormDto.setPhone(applicationForm.getPhone());
        applicationFormDto.setLocation(applicationForm.getLocation());
        applicationFormDto.setResumeUrl(applicationForm.getResumeUrl());
        applicationFormDto.setCoverLetterUrl(applicationForm.getCoverLetterUrl());
        applicationFormDto.setJobTitle(applicationForm.getVacancy().getJobTitle());
        applicationFormDto.setUsername(applicationForm.getVacancy().getAppUser().getUsername());
        return applicationFormDto;
    }
}
