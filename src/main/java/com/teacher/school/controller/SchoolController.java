package com.teacher.school.controller;

import com.teacher.image.model.Image;
import com.teacher.school.model.School;
import com.teacher.school.model.SchoolDto;
import com.teacher.school.service.SchoolService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping(path = "api/teacher")
@AllArgsConstructor
public class SchoolController {
    private final SchoolService schoolService;
    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher publisher;






    private SchoolDto convertSchoolToDto(School school){
        SchoolDto schoolDto=new SchoolDto();

        return schoolDto;
    }

    private String applicationUrl(HttpServletRequest request){
        return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }

    private Image uploadPicture(MultipartFile multipartFile) throws IOException {
        Image picture=new Image(multipartFile.getOriginalFilename(),
                multipartFile.getContentType(),
                multipartFile.getBytes());
        return picture;
    }
}
