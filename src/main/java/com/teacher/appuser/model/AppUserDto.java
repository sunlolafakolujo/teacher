package com.teacher.appuser.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teacher.contact.model.Contact;
import com.teacher.qualification.model.Qualification;
import com.teacher.reference.model.Referee;
import com.teacher.staticdata.Gender;
import com.teacher.staticdata.MeansOfIdentification;
import com.teacher.staticdata.Title;
import com.teacher.staticdata.UserType;
import com.teacher.userrole.model.UserRole;
import com.teacher.workexperience.model.WorkExperience;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDto {

    @JsonIgnore
    private Long id;

    private UserType userType;

    private String username;

    private String password;

    private String email;

    private String phone;

    private String firstName;

    private String lastName;

    private String schoolName;

    private Title title;

    private Gender gender;

    private String picUrl;

    private String webSite;

    private Integer age;

    private LocalDate dateOfBirth;

    private String rcNumber;

    private MeansOfIdentification meansOfIdentification;

    private LocalDate meansOfIdentificationIssueDate;

    private LocalDate meansOfIdentificationExpiryDate;

    private String streetNumber;

    private String streetName;

    private String city;

    private String landMark;

    private String postZipCode;

    private String stateProvince;

    private String country;

    private List<Qualification> qualifications;

    private List<WorkExperience> workExperiences;

    private List<Referee> referees;
}
