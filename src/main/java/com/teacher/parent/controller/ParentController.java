package com.teacher.parent.controller;

import com.teacher.appuser.exception.AppUserNotFoundException;
import com.teacher.event.RegistrationCompleteEvent;
import com.teacher.image.model.Image;
import com.teacher.parent.model.NewParent;
import com.teacher.parent.model.Parent;
import com.teacher.parent.service.ParentService;
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

@RestController
@RequestMapping(path = "api/teacher")
@AllArgsConstructor
public class ParentController {
    private final ParentService parentService;
    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher publisher;

    @PostMapping(value = {"/addParent"}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<NewParent> addParent(@RequestPart("parent") NewParent newParent,
                                               @RequestPart("image")MultipartFile multipartFile,
                                               HttpServletRequest request) throws UserRoleNotFoundException,
                                                                                        AppUserNotFoundException {
        Image image=new Image();
        try {
            image=uploadPicture(multipartFile);
        }catch (IOException e){
           System.out.println(e.getMessage());
        }
        newParent.setImage(image);
        Parent parent=modelMapper.map(newParent,Parent.class);
        Parent post=parentService.addParent(parent);
        publisher.publishEvent(new RegistrationCompleteEvent(post.getAppUser(),applicationUrl(request)));
        NewParent posted=modelMapper.map(post,NewParent.class);
        return new ResponseEntity<>(posted, HttpStatus.CREATED);
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
