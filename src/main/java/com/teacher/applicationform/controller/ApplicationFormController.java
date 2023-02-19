package com.teacher.applicationform.controller;

import com.teacher.applicationform.exception.ApplicationFormNotFoundException;
import com.teacher.applicationform.model.ApplicationForm;
import com.teacher.applicationform.model.ApplicationFormDto;
import com.teacher.applicationform.model.NewApplicationForm;
import com.teacher.applicationform.service.ApplicationFormService;
import com.teacher.appuser.exception.AppUserNotFoundException;
import com.teacher.event.ApplicationFormEvent;
import com.teacher.vacancy.exception.VacancyNotFoundException;
import com.teacher.vacancy.model.Vacancy;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(path = "api/teacher/applicationForm")
@AllArgsConstructor
public class ApplicationFormController {
    private final ApplicationFormService applicationFormService;
    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher publisher;

    @PostMapping("/saveApplication")
    public ResponseEntity<NewApplicationForm> saveApplication(@RequestBody @Valid NewApplicationForm newApplicationForm)
                                                                                        throws AppUserNotFoundException {
        ApplicationForm applicationForm=modelMapper.map(newApplicationForm, ApplicationForm.class);
        ApplicationForm post=applicationFormService.saveApplication(applicationForm);
        NewApplicationForm posted=modelMapper.map(post, NewApplicationForm.class);
        publisher.publishEvent(new ApplicationFormEvent(posted.getTeacher().getAppUser()));
        return new ResponseEntity<>(posted, CREATED);
    }

    @GetMapping("/findApplicationById")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApplicationFormDto> getApplicationById(@RequestParam("id") Long id) throws ApplicationFormNotFoundException {
        ApplicationForm applicationForm=applicationFormService.findApplicationById(id);
        ApplicationFormDto applicationFormDto=convertApplicationFormToDto(applicationForm);
        return new ResponseEntity<>(applicationFormDto, OK);
    }

    @GetMapping("/findApplicationByVacancyCode")
    @PreAuthorize("hasAnyRole('SCHOOL','PARENT')")
    public ResponseEntity<ApplicationFormDto> getApplicationByVacancyCode(@RequestParam("vacancyCode") String vacancyCode)
                                                                                    throws VacancyNotFoundException {
        ApplicationForm applicationForm= applicationFormService.findByVacancy(vacancyCode);
        return new ResponseEntity<>(convertApplicationFormToDto(applicationForm), OK);
    }

    @GetMapping("/findAllApplications")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ApplicationFormDto>> getAllApplications(@RequestParam(defaultValue = "0")Integer pageNumber){
        return new ResponseEntity<>(applicationFormService
                .findAllApplications(pageNumber)
                .stream()
                .map(this::convertApplicationFormToDto)
                .collect(Collectors.toList()), OK);
    }

    @DeleteMapping("/deleteApplicationById")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteApplicationById(@RequestParam("id") Long id) throws ApplicationFormNotFoundException {
        applicationFormService.deleteApplicationById(id);
        return ResponseEntity.ok().body("Vacancy ID "+id+" has being deleted");
    }

    @DeleteMapping("/deleteAllApplications")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteAllApplications() {
        applicationFormService.deleteAllApplications();
        return ResponseEntity.ok().body("All vacancies has being deleted");
    }



    private ApplicationFormDto convertApplicationFormToDto(ApplicationForm applicationForm){
        ApplicationFormDto applicationFormDto=new ApplicationFormDto();
        applicationFormDto.setJobTitle(applicationForm.getVacancy().getJobTitle());
        applicationFormDto.setFirstName(applicationForm.getTeacher().getFirstName());
        applicationFormDto.setLastName(applicationForm.getTeacher().getLastName());
        applicationFormDto.setEmail(applicationForm.getTeacher().getAppUser().getEmail());
        applicationFormDto.setPhone(applicationForm.getTeacher().getAppUser().getMobile());
        applicationFormDto.setLocation(applicationForm.getLocation());
        return applicationFormDto;
    }
}
