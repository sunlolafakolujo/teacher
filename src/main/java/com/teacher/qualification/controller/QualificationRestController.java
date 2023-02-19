package com.teacher.qualification.controller;

import com.teacher.qualification.exception.QualificationNotFoundException;
import com.teacher.qualification.model.NewQualification;
import com.teacher.qualification.model.Qualification;
import com.teacher.qualification.model.QualificationDto;
import com.teacher.qualification.service.QualificationService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="api/teacher/qualification")
@AllArgsConstructor
public class QualificationRestController {

    private final QualificationService qualificationService;
    private final ModelMapper modelMapper;

    @PostMapping("/saveQualification")
    public ResponseEntity<NewQualification> saveQualification(@RequestBody @Valid NewQualification newQualification){
        Qualification qualification=modelMapper.map(newQualification, Qualification.class);
        Qualification postQualification=qualificationService.saveQualification(qualification);
        NewQualification posted =modelMapper.map(postQualification, NewQualification.class);
        return new ResponseEntity<>(posted, HttpStatus.CREATED);
    }

    @GetMapping("/findQualificationById")
    public ResponseEntity<QualificationDto> getQualificationById(@RequestParam("id") Long id) throws QualificationNotFoundException {
        Qualification qualification=qualificationService.findQualificationById(id);
        QualificationDto qualificationDto=convertQualificationDto(qualification);
        return new ResponseEntity<>(qualificationDto, HttpStatus.OK);
    }

    @GetMapping("/findAllQualifications")
    public ResponseEntity<List<QualificationDto>> getAllQualifications(@RequestParam(defaultValue = "")Integer pageNumber){
        return new ResponseEntity<>(qualificationService.findAllQualifications(pageNumber)
                .stream()
                .map(this::convertQualificationDto)
                .collect(Collectors.toList()),HttpStatus.OK);
    }

    @DeleteMapping("/deleteQualificationById")
    public ResponseEntity<?> deleteQualificationById(@RequestParam("id") Long id) throws QualificationNotFoundException {
        qualificationService.deleteQualificationById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteAllQualifications")
    public ResponseEntity<?> deleteAllQualifications(){
        qualificationService.deleteAllQualification();
        return ResponseEntity.noContent().build();
    }



























    private QualificationDto convertQualificationDto(Qualification qualification){

        QualificationDto qualificationDto=new QualificationDto();

        qualificationDto.setSubject(qualification.getSubject());
        qualificationDto.setDegreeTitle(qualification.getDegreeTitle());
        qualificationDto.setClassOfDegree(qualification.getClassOfDegree());
        qualificationDto.setInstitutionName(qualification.getInstitutionName());
        qualificationDto.setCurrentQualification(qualification.getCurrentQualification());
        qualificationDto.setStartDate(qualification.getStartDate());
        qualificationDto.setEndDate(qualification.getEndDate());
        qualificationDto.setInstitutionAddress(qualification.getInstitutionAddress());
        return qualificationDto;
    }
}
