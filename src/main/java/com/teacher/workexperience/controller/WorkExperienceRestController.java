package com.teacher.workexperience.controller;

import com.teacher.workexperience.exception.WorkExperienceNotFoundException;
import com.teacher.workexperience.model.NewWorkExperience;
import com.teacher.workexperience.model.WorkExperience;
import com.teacher.workexperience.model.WorkExperienceDto;
import com.teacher.workexperience.service.WorkExperienceService;
import lombok.AllArgsConstructor;
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
@RequestMapping(path = "api/teacher/workExperience")
@AllArgsConstructor
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

    @GetMapping("/findWorkExperienceById")
    public ResponseEntity<WorkExperienceDto> getWorkExperienceById(@RequestParam("id") Long id)
                                                            throws WorkExperienceNotFoundException {
        WorkExperience workExperience=workExperienceService.findWorkExperienceById(id);
        return new ResponseEntity<>(convertWorkExperienceToDto(workExperience), HttpStatus.OK);
    }

    @GetMapping("/findAllWorkExperiences")
    public ResponseEntity<List<WorkExperienceDto>> getAllWorkExperiences(@RequestParam(defaultValue = "0")Integer pageNumber){
        return new ResponseEntity<>(workExperienceService.findAllWorkExperience(pageNumber)
                .stream()
                .map(this::convertWorkExperienceToDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @DeleteMapping("/deleteWorkExperienceById")
    public ResponseEntity<?> deleteWorkExperienceById(@RequestParam("id") Long id) throws WorkExperienceNotFoundException {
        workExperienceService.deleteWorkExperienceById(id);
        return ResponseEntity.ok().body("Work Experience ID "+id+" has being deleted");
    }

    @DeleteMapping("/deleteAllWorkExperiences")
    public ResponseEntity<?> deleteAllWorkExperiences(){
        workExperienceService.deleteAllWorkExperience();
        return ResponseEntity.ok().body("Work Experiences has being deleted");
    }






























    WorkExperienceDto convertWorkExperienceToDto(WorkExperience workExperience){

        WorkExperienceDto workExperienceDto=new WorkExperienceDto();

        workExperienceDto.setCompany(workExperience.getCompany());
        workExperienceDto.setPosition(workExperience.getPosition());
        workExperienceDto.setCurrentWork(workExperience.getCurrentWork());
        workExperienceDto.setStartDate(workExperience.getStartDate());
        workExperienceDto.setEndDate(workExperience.getEndDate());
        return workExperienceDto;
    }
}
