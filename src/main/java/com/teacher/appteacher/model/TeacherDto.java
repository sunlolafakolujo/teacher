package com.teacher.appteacher.model;

import com.teacher.appuser.model.AppUser;
import com.teacher.image.model.Image;
import com.teacher.meansofidentification.model.MeanOfIdentification;
import com.teacher.qualification.model.Qualification;
import com.teacher.reference.model.Referee;
import com.teacher.staticdata.Gender;
import com.teacher.staticdata.MeansOfIdentification;
import com.teacher.staticdata.Title;
import com.teacher.workexperience.model.WorkExperience;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDto {
    private Long id;
    private String teacherCode;
    private Title title;
    private String firstName;
    private String lastName;
    private String otherName;
    private Gender gender;
    private LocalDate dateOfBirth;
    private Integer age;
    private String email;
    private String mobile;
    private String houseNumber;
    private String streetName;
    private String city;
    private String landmark;
    private String stateProvince;
    private String country;
    private MeansOfIdentification meansOfIdentification;
    private String meansOfIdentificationRefNumber;
    private LocalDate meansOfIdentificationIssueDate;
    private LocalDate meansOfIdentificationExpiryDate;
    private Image image;
    private List<Qualification> qualifications;
    private List<WorkExperience> workExperiences;
    private List<Referee> referees;
}
