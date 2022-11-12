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
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDto {

    @JsonIgnore
    private Long id;

    private String userId;

    private UserType userType;

    private Title title;

    private String firstName;

    private String lastName;

    private Gender gender;

    private LocalDate dateOfBirth;

    private Integer age;

    private String schoolName;

    private String username;

    private String email;

    private String phone;

    private String picUrl;

    private String webSite;

    private String rcNumber;

    private MeansOfIdentification meansOfIdentification;

    private String meansOfIdentificationRefNumber;

    private LocalDate meansOfIdentificationIssueDate;

    private LocalDate meansOfIdentificationExpiryDate;

    private Collection<Contact> contacts=new ArrayList<>();

    private Collection<Qualification> qualifications=new HashSet<>();

    private Collection<WorkExperience> workExperiences=new HashSet<>();

    private Collection<Referee> referees=new HashSet<>();

    private Collection<UserRole> userRoles;
}
