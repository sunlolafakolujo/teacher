package com.teacher.appteacher.controller;

import com.teacher.appteacher.exception.TeacherNotFoundException;
import com.teacher.appteacher.model.NewTeacher;
import com.teacher.appteacher.model.Teacher;
import com.teacher.appteacher.service.TeacherService;
import com.teacher.appuser.exception.AppUserNotFoundException;
import com.teacher.event.RegistrationCompleteEvent;
import com.teacher.image.model.Image;
import com.teacher.userrole.exception.UserRoleNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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
                                                 @RequestPart("picture")MultipartFile multipartFile,
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




    private Image uploadImage(MultipartFile multipartFile) throws IOException {
        Image image=new Image(multipartFile.getOriginalFilename(),
                multipartFile.getContentType(),
                multipartFile.getBytes());
        return image;
    }

    private String applicationUrl(HttpServletRequest request){
        return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }
}
