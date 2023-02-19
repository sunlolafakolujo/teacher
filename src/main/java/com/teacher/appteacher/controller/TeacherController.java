package com.teacher.appteacher.controller;

import com.teacher.appteacher.exception.TeacherNotFoundException;
import com.teacher.appteacher.model.NewTeacher;
import com.teacher.appteacher.model.Teacher;
import com.teacher.appteacher.model.TeacherDto;
import com.teacher.appteacher.model.UpdateTeacher;
import com.teacher.appteacher.service.TeacherService;
import com.teacher.appuser.exception.AppUserNotFoundException;
import com.teacher.event.RegistrationCompleteEvent;
import com.teacher.image.model.Image;
import com.teacher.qualification.model.Qualification;
import com.teacher.reference.model.Referee;
import com.teacher.userrole.exception.UserRoleNotFoundException;
import com.teacher.workexperience.model.WorkExperience;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(path = "api/teacher")
@AllArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;
    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher publisher;

    @PostMapping(value = {"/addTeacher"},consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<NewTeacher> addTeacher(@RequestPart("teacher") NewTeacher newTeacher,
                                                 @RequestPart("image")MultipartFile multipartFile,
                                                 HttpServletRequest request) throws TeacherNotFoundException,
                                                 UserRoleNotFoundException, AppUserNotFoundException {
        Image image=new Image();
        try {
            image=uploadImage(multipartFile);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        newTeacher.setImage(image);
        Teacher teacher=modelMapper.map(newTeacher,Teacher.class);
        Teacher post=teacherService.addTeacher(teacher);
        NewTeacher posted=modelMapper.map(post,NewTeacher.class);
        publisher.publishEvent(new RegistrationCompleteEvent(posted.getAppUser(), applicationUrl(request)));
        return new ResponseEntity<>(posted, CREATED);
    }

    @GetMapping("/findTeacherById")
    public ResponseEntity<TeacherDto> getTeacherById(@RequestParam("id")Long id) throws TeacherNotFoundException {
        Teacher teacher=teacherService.fetchById(id);
        return new ResponseEntity<>(convertTeacherToDto(teacher),OK);
    }

    @GetMapping("/findTeacherByTeacherCode")
    public ResponseEntity<TeacherDto> getTeacherByCode(@RequestParam("teacherCode")String teacherCode) throws TeacherNotFoundException {
        Teacher teacher=teacherService.fetchByTeacherCode(teacherCode);
        return new ResponseEntity<>(convertTeacherToDto(teacher),OK);
    }

    @GetMapping("/findAllTeachersOByFirstOrLastName")
    public ResponseEntity<List<TeacherDto>> getAllTeacherOrFirstOrLastName(@RequestParam(defaultValue = "0")Integer pageNumber,
                                                                           @RequestParam(defaultValue = "")String searchKey){
        return new ResponseEntity<>(teacherService.fetchAllTeachersOrByFirstOrLastName(searchKey, pageNumber)
                .stream()
                .map(this::convertTeacherToDto)
                .collect(Collectors.toList()), OK);
    }

    @PutMapping("/updateTeacher")
    public ResponseEntity<UpdateTeacher> edit(@RequestBody UpdateTeacher updateTeacher,@RequestParam("id") Long id)
                                                                                throws TeacherNotFoundException {
        Teacher teacher=modelMapper.map(updateTeacher,Teacher.class);
        Teacher post=teacherService.updateTeacher(teacher,id);
        UpdateTeacher posted=modelMapper.map(post,UpdateTeacher.class);
        return new ResponseEntity<>(posted,OK);
    }

    @DeleteMapping("/deleteTeacher")
    public ResponseEntity<?> deleteTeacherById(@RequestParam("id")Long id) throws TeacherNotFoundException {
        teacherService.deleteTeacherById(id);
        return ResponseEntity.ok().body("Teacher ID "+id+" has being deleted");
    }

    @DeleteMapping("/deleteAllTeachers")
    public ResponseEntity<?> deleteAllTeachers(){
        teacherService.deleteAllTeacher();
        return ResponseEntity.ok().body("Teachers record has being deleted from the database");
    }


    private TeacherDto convertTeacherToDto(Teacher teacher){
        TeacherDto teacherDto=new TeacherDto();
        teacherDto.setId(teacher.getId());
        teacherDto.setTitle(teacher.getTitle());
        teacherDto.setTeacherCode(teacher.getTeacherCode());
        teacherDto.setFirstName(teacher.getFirstName());
        teacherDto.setLastName(teacher.getLastName());
        teacherDto.setOtherName(teacher.getOtherName());
        teacherDto.setGender(teacher.getGender());
        teacherDto.setDateOfBirth(teacher.getDateOfBirth());
        teacherDto.setAge(teacher.getAge());
        teacherDto.setEmail(teacher.getAppUser().getEmail());
        teacherDto.setMobile(teacher.getAppUser().getMobile());
        teacherDto.setImage(teacher.getImage());
        teacherDto.setHouseNumber(teacher.getAppUser().getContact().getHouseNumber());
        teacherDto.setStreetName(teacher.getAppUser().getContact().getStreetName());
        teacherDto.setCity(teacher.getAppUser().getContact().getCity());
        teacherDto.setLandmark(teacher.getAppUser().getContact().getLandmark());
        teacherDto.setStateProvince(teacher.getAppUser().getContact().getStateProvince());
        teacherDto.setCountry(teacher.getAppUser().getContact().getCountry());
        teacherDto.setMeansOfIdentification(teacher.getMeanOfIdentification().getMeansOfIdentification());
        teacherDto.setMeansOfIdentificationRefNumber(teacher.getMeanOfIdentification().getMeansOfIdentificationRefNumber());
        teacherDto.setMeansOfIdentificationIssueDate(teacher.getMeanOfIdentification().getMeansOfIdentificationIssueDate());
        teacherDto.setMeansOfIdentificationExpiryDate(teacher.getMeanOfIdentification().getMeansOfIdentificationExpiryDate());
        teacherDto.setQualifications(teacher.getQualifications().stream().map(q->{
            Qualification qualification=new Qualification();
            qualification.setDegreeTitle(q.getDegreeTitle());
            qualification.setSubject(q.getSubject());
            qualification.setClassOfDegree(q.getClassOfDegree());
            qualification.setCurrentQualification(q.getCurrentQualification());
            qualification.setStartDate(q.getStartDate());
            qualification.setEndDate(q.getEndDate());
            qualification.setInstitutionName(q.getInstitutionName());
            qualification.setInstitutionAddress(q.getInstitutionAddress());
            qualification.setCertificates(q.getCertificates());
            return qualification;
        }).collect(Collectors.toList()));
        teacherDto.setWorkExperiences(teacher.getWorkExperiences().stream().map(w->{
            WorkExperience workExperience=new WorkExperience();
            workExperience.setCompany(w.getCompany());
            workExperience.setPosition(w.getPosition());
            workExperience.setCurrentWork(w.getCurrentWork());
            workExperience.setStartDate(w.getStartDate());
            workExperience.setEndDate(w.getEndDate());
            workExperience.setResume(w.getResume());
            return workExperience;
        }).collect(Collectors.toList()));
        teacherDto.setReferees(teacher.getReferees().stream().map(r->{
            Referee referee=new Referee();
            referee.setTitle(r.getTitle());
            referee.setFirstName(r.getFirstName());
            referee.setLastName(r.getLastName());
            referee.setEmail(r.getEmail());
            referee.setPhone(r.getPhone());
            referee.setReferenceLetters(r.getReferenceLetters());
            return referee;
        }).collect(Collectors.toList()));

        return teacherDto;
    }

    private Image uploadImage(MultipartFile multipartFiles) throws IOException {
        Image image = new Image(multipartFiles.getOriginalFilename(),
                    multipartFiles.getContentType(),
                    multipartFiles.getBytes());
        return image;
    }

    private String applicationUrl(HttpServletRequest request){
        return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }
}
