package com.teacher.parent.controller;

import com.teacher.appuser.exception.AppUserNotFoundException;
import com.teacher.event.RegistrationCompleteEvent;
import com.teacher.image.model.Image;
import com.teacher.parent.model.NewParent;
import com.teacher.parent.model.Parent;
import com.teacher.parent.model.ParentDto;
import com.teacher.parent.service.ParentService;
import com.teacher.reference.model.Referee;
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
import java.util.stream.Collectors;

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
//        publisher.publishEvent(new RegistrationCompleteEvent(post.getAppUser(),applicationUrl(request)));
        NewParent posted=modelMapper.map(post,NewParent.class);
        publisher.publishEvent(new RegistrationCompleteEvent(posted.getAppUser(),applicationUrl(request)));
        return new ResponseEntity<>(posted, HttpStatus.CREATED);
    }



    private ParentDto convertParentToDto(Parent parent){
        ParentDto parentDto=new ParentDto();
        parentDto.setParentId(parent.getParentId());
        parentDto.setTitle(parent.getTitle());
        parentDto.setFirstName(parent.getFirstName());
        parentDto.setLastName(parent.getLastName());
        parentDto.setGender(parent.getGender());
        parentDto.setEmail(parent.getAppUser().getEmail());
        parentDto.setMobile(parent.getAppUser().getMobile());
        parentDto.setImage(parent.getImage());
        parentDto.setMeansOfIdentification(parent.getMeanOfIdentification().getMeansOfIdentification());
        parentDto.setMeansOfIdentificationRefNumber(parent.getMeanOfIdentification().getMeansOfIdentificationRefNumber());
        parentDto.setMeansOfIdentificationIssueDate(parent.getMeanOfIdentification().getMeansOfIdentificationIssueDate());
        parentDto.setMeansOfIdentificationExpiryDate(parent.getMeanOfIdentification().getMeansOfIdentificationExpiryDate());
        parentDto.setHomeNumber(parent.getAppUser().getContact().getHouseNumber());
        parentDto.setStreetName(parent.getAppUser().getContact().getStreetName());
        parentDto.setLandmark(parent.getAppUser().getContact().getLandMark());
        parentDto.setStateProvince(parent.getAppUser().getContact().getStateProvince());
        parentDto.setCountry(parent.getAppUser().getContact().getCountry());
        parentDto.setProfession(parent.getProfession());
        parentDto.setEmployerName(parent.getEmployer().getEmployerName());
        parentDto.setEmployerEmail(parent.getEmployer().getEmployerEmail());
        parentDto.setEmployerPhone(parent.getEmployer().getEmployerMobile());
        parentDto.setEmployerOfficeNumber(parent.getEmployer().getEmployerAddress().getHouseNumber());
        parentDto.setEmployerOfficeStreet(parent.getEmployer().getEmployerAddress().getStreetName());
        parentDto.setEmployerOfficeCity(parent.getEmployer().getEmployerAddress().getCity());
        parentDto.setEmployerOfficeLandmark(parent.getEmployer().getEmployerAddress().getLandMark());
        parentDto.setEmployerOfficeState(parent.getEmployer().getEmployerAddress().getStateProvince());
        parentDto.setEmployerOfficeCountry(parent.getEmployer().getEmployerAddress().getCountry());
        parentDto.setReferees(parent.getReferees().stream().map(r -> {
            Referee referee=new Referee();
            referee.setFirstName(r.getFirstName());
            referee.setLastName(r.getLastName());
            referee.setEmail(r.getEmail());
            referee.setPhone(r.getPhone());
            return referee;
        }).collect(Collectors.toList()));

        return parentDto;
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
