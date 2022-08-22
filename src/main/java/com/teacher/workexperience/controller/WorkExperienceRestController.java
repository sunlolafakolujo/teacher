package com.teacher.workexperience.controller;

import com.teacher.workexperience.exception.WorkExperienceNotFoundException;
import com.teacher.workexperience.model.NewWorkExperience;
import com.teacher.workexperience.model.WorkExperience;
import com.teacher.workexperience.model.WorkExperienceDto;
import com.teacher.workexperience.service.WorkExperienceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/workExperience")
@RequiredArgsConstructor
public class WorkExperienceRestController {

    private final WorkExperienceService workExperienceService;

    private final ModelMapper modelMapper;

    @PostMapping("/saveWorkExperience")
    public ResponseEntity<NewWorkExperience> saveWorkExperience(@RequestBody NewWorkExperience newWorkExperience){
        WorkExperience workExperience=modelMapper.map(newWorkExperience, WorkExperience.class);

        WorkExperience post=workExperienceService.saveWorkExperience(workExperience);

        NewWorkExperience posted=modelMapper.map(post, NewWorkExperience.class);

        return new ResponseEntity<>(posted, HttpStatus.CREATED);
    }

    @GetMapping("/findWorkExperienceById/{id}")
    public ResponseEntity<WorkExperienceDto> getWorkExperienceById(@PathVariable(value = "id") Long id)
                                                            throws WorkExperienceNotFoundException {

        WorkExperience workExperience=workExperienceService.findWorkExperienceById(id);

        WorkExperienceDto workExperienceDto=convertWorkExperienceToDto(workExperience);

        return new ResponseEntity<>(workExperienceDto, HttpStatus.OK);
    }

    @GetMapping("/findAllWorkExperiences")
    public ResponseEntity<List<WorkExperienceDto>> getAllWorkExperiences(){
        Pageable pageable= PageRequest.of(1, 10);

        return new ResponseEntity<>(workExperienceService.findAllWorkExperience(pageable)
                .stream()
                .map(this::convertWorkExperienceToDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @DeleteMapping("/deleteWorkExperienceById/{id}")
    public ResponseEntity<?> deleteWorkExperienceById(@PathVariable(value = "id") Long id) throws WorkExperienceNotFoundException {
        workExperienceService.deleteWorkExperienceById(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteAllWorkExperiences")
    public ResponseEntity<?> deleteAllWorkExperiences(){
        workExperienceService.deleteAllWorkExperience();

        return ResponseEntity.noContent().build();
    }






























    WorkExperienceDto convertWorkExperienceToDto(WorkExperience workExperience){

        WorkExperienceDto workExperienceDto=new WorkExperienceDto();

        workExperienceDto.setCompany(workExperience.getCompany());
        workExperienceDto.setPosition(workExperience.getPosition());
        workExperienceDto.setCurrentWork(workExperience.getCurrentWork());
        workExperienceDto.setStartDate(workExperience.getStartDate());
        workExperienceDto.setEndDate(workExperience.getEndDate());
        workExperienceDto.setAppUserFirstName(workExperience.getAppUser().getFirstName());
        workExperienceDto.setAppUserLastName(workExperience.getAppUser().getLastName());
        workExperienceDto.setAppUserEmail(workExperience.getAppUser().getEmail());
        workExperienceDto.setAppUserPhone(workExperience.getAppUser().getPhone());

        return workExperienceDto;
    }

}
