package com.teacher.appparent.controller;

import com.teacher.appparent.model.UpdateParent;
import com.teacher.appuser.exception.AppUserNotFoundException;
import com.teacher.event.RegistrationCompleteEvent;
import com.teacher.image.model.Image;
import com.teacher.appparent.exception.ParentNotFoundException;
import com.teacher.appparent.model.NewParent;
import com.teacher.appparent.model.Parent;
import com.teacher.appparent.model.ParentDto;
import com.teacher.appparent.service.ParentService;
import com.teacher.reference.model.Referee;
import com.teacher.userrole.exception.UserRoleNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

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
        return new ResponseEntity<>(posted, CREATED);
    }

    @GetMapping("/findParentById")
    public ResponseEntity<ParentDto> getParentById(@RequestParam("id") Long id) throws ParentNotFoundException {
        Parent parent=parentService.fetchParentById(id);
        return new ResponseEntity<>(convertParentToDto(parent), OK);
    }

    @GetMapping("/findParentByCode")
    public ResponseEntity<ParentDto> getParentByCode(@RequestParam("parentId")String parentId) throws ParentNotFoundException {
        Parent parent=parentService.fetchByParentId(parentId);
        return new ResponseEntity<>(convertParentToDto(parent),OK);
    }

    @GetMapping("/findParentByEmailOrUsernameOrMobile")
    public ResponseEntity<ParentDto> getParentByEmailOrUsernameOrMobile(@RequestParam("searchKey") String searchKey)
                                                                                   throws AppUserNotFoundException {
        Parent parent=parentService.fetchParentByUsernameOrEmailOrMobileOrUserId(searchKey);
        return new ResponseEntity<>(convertParentToDto(parent),OK);
    }

    @GetMapping("/findAllParentOrByFirstOrLastName")
    public ResponseEntity<List<ParentDto>> getAllParentByFirstOrLastName(@RequestParam(defaultValue = "0")Integer pageNumber,
                                                                         @RequestParam(defaultValue = "")String searchKey){
        return new ResponseEntity<>(parentService.fetchAllParentOrByFirstOrLastName(searchKey,pageNumber)
                .stream()
                .map(this::convertParentToDto)
                .collect(Collectors.toList()), OK);
    }

    @PutMapping("/updateParentRecord")
    public ResponseEntity<UpdateParent> edit(@RequestBody UpdateParent updateParent,@RequestParam("id") Long id)
                                                                                    throws ParentNotFoundException {
        Parent parent=modelMapper.map(updateParent,Parent.class);
        Parent post=parentService.updateParent(parent,id);
        UpdateParent posted=modelMapper.map(post,UpdateParent.class);
        return new ResponseEntity<>(posted,OK);
    }

    @DeleteMapping("/deleteParent")
    public ResponseEntity<?> deleteParentById(@RequestParam("id")Long id) throws ParentNotFoundException {
        parentService.deleteParentById(id);
        return ResponseEntity.ok().body("Parent with ID "+id+" has being deleted from the database");
    }

    @DeleteMapping("/deleteAllParent")
    public ResponseEntity<?> deleteAllParents(){
        parentService.deleteAllParent();
        return ResponseEntity.ok().body("All parent has being deleted from the database");
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
        parentDto.setLandmark(parent.getAppUser().getContact().getLandmark());
        parentDto.setStateProvince(parent.getAppUser().getContact().getStateProvince());
        parentDto.setCountry(parent.getAppUser().getContact().getCountry());
        parentDto.setProfession(parent.getProfession());
        parentDto.setEmployerName(parent.getEmployer().getEmployerName());
        parentDto.setEmployerEmail(parent.getEmployer().getEmployerEmail());
        parentDto.setEmployerPhone(parent.getEmployer().getEmployerMobile());
        parentDto.setEmployerOfficeNumber(parent.getEmployer().getEmployerAddress().getHouseNumber());
        parentDto.setEmployerOfficeStreet(parent.getEmployer().getEmployerAddress().getStreetName());
        parentDto.setEmployerOfficeCity(parent.getEmployer().getEmployerAddress().getCity());
        parentDto.setEmployerOfficeLandmark(parent.getEmployer().getEmployerAddress().getLandmark());
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
