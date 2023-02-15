package com.teacher.school.controller;

import com.teacher.appuser.exception.AppUserNotFoundException;
import com.teacher.event.RegistrationCompleteEvent;
import com.teacher.image.model.Image;
import com.teacher.school.exception.SchoolNotFoundException;
import com.teacher.school.model.NewSchool;
import com.teacher.school.model.School;
import com.teacher.school.model.SchoolDto;
import com.teacher.school.model.UpdateSchool;
import com.teacher.school.service.SchoolService;
import com.teacher.userrole.exception.UserRoleNotFoundException;
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
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(path = "api/teacher")
@AllArgsConstructor
public class SchoolController {
    private final SchoolService schoolService;
    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher publisher;

    @PostMapping(value ={"/addSchool"}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<NewSchool> addSchool(@RequestPart("school") NewSchool newSchool,
                                               @RequestPart("cacCertificate") MultipartFile multipartFile,
                                               HttpServletRequest request) throws UserRoleNotFoundException,
                                                                AppUserNotFoundException, SchoolNotFoundException {
        Image image=new Image();
        try {
            image=uploadPicture(multipartFile);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        newSchool.setCacCertificate(image);
        School school=modelMapper.map(newSchool,School.class);
        School post=schoolService.addSchool(school);
        NewSchool posted=modelMapper.map(post,NewSchool.class);
        publisher.publishEvent(new RegistrationCompleteEvent(posted.getAppUser(),applicationUrl(request)));
        return new ResponseEntity<>(posted, CREATED);
    }

    @GetMapping("/findSchool")
    public ResponseEntity<SchoolDto> getSchoolById(@RequestParam("id")Long id) throws SchoolNotFoundException {
        School school=schoolService.fetchById(id);
        return new ResponseEntity<>(convertSchoolToDto(school),OK);
    }

    @GetMapping("/findBySchoolId")
    public ResponseEntity<SchoolDto> getBySchoolId(@RequestParam("searchKey") String searchKey) throws SchoolNotFoundException {
        School school=schoolService.fetchBySchoolIdOrNameOrRcNumber(searchKey);
        return new ResponseEntity<>(convertSchoolToDto(school),OK);
    }

    @GetMapping("/findSchoolByEmailOrUsernameOrPhoneOrUserId")
    public ResponseEntity<SchoolDto> getSchoolByEmailOrUsernameOrPhoneOrUserId(@RequestParam("searchKey") String searchKey) throws AppUserNotFoundException {
        School school=schoolService.fetchByEmailOrUsernameOrPhoneOrUserId(searchKey);
        return new ResponseEntity<>(convertSchoolToDto(school),OK);
    }

    @GetMapping("findAllSchools")
    public ResponseEntity<List<SchoolDto>> getAllSchools(@RequestParam(defaultValue = "0")Integer pageNumber){
        return new ResponseEntity<>(schoolService.fetchAllSchools(pageNumber)
                .stream()
                .map(this::convertSchoolToDto)
                .collect(Collectors.toList()), OK);
    }

    @PutMapping("/updateSchool")
    public ResponseEntity<UpdateSchool> edit(@RequestBody UpdateSchool updateSchool,
                                             @RequestParam("id")Long id) throws SchoolNotFoundException {
        School school=modelMapper.map(updateSchool,School.class);
        School post=schoolService.updateSchool(school,id);
        UpdateSchool posted=modelMapper.map(post,UpdateSchool.class);
        return new ResponseEntity<>(posted,OK);
    }

    @DeleteMapping("/deleteSchool")
    public ResponseEntity<?> deleteSchoolById(@RequestParam("id") Long id) throws SchoolNotFoundException {
        schoolService.deleteSchoolById(id);
        return ResponseEntity.ok().body("School ID "+id+" has being deleted");
    }

    @DeleteMapping("/deleteAllSchools")
    public ResponseEntity<?> deleteAllSchools(){
        schoolService.deleteAllSchool();
        return ResponseEntity.ok().body("Schools has being deleted");
    }






    private SchoolDto convertSchoolToDto(School school){
        SchoolDto schoolDto=new SchoolDto();
        schoolDto.setId(school.getId());
        schoolDto.setSchoolId(school.getSchoolId());
        schoolDto.setSchoolName(school.getSchoolName());
        schoolDto.setYearFounded(school.getYearFounded());
        schoolDto.setAge(school.getAge());
        schoolDto.setRcNumber(school.getRcNumber());
        schoolDto.setSchoolWebSite(school.getSchoolWebSite());
        schoolDto.setEmail(school.getAppUser().getEmail());
        schoolDto.setPhone(school.getAppUser().getMobile());
        schoolDto.setStreetNumber(school.getAppUser().getContact().getHouseNumber());
        schoolDto.setStreetName(school.getAppUser().getContact().getStreetName());
        schoolDto.setCity(school.getAppUser().getContact().getCity());
        schoolDto.setLandmark(school.getAppUser().getContact().getLandMark());
        schoolDto.setStateProvince(school.getAppUser().getContact().getStateProvince());
        schoolDto.setCountry(school.getAppUser().getContact().getCountry());
        schoolDto.setCacCertificate(school.getCacCertificate());
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
