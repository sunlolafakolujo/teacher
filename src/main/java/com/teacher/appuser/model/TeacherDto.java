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
public class TeacherDto {

    @JsonIgnore
    private Long id;

    private UserType userType;

    private Title title;

    private String firstName;

    private String lastName;

    private Gender gender;

    private String username;

    private String password;

    private String email;

    private String phone;

    private LocalDate dateOfBirth;

    private String picUrl;

    private MeansOfIdentification meansOfIdentification;

    private String meansOfIdentificationIssueDate;

    private String meansOfIdentificationExpiryDate;

    private Contact contact;

    private List<Qualification> qualifications;

    private List<WorkExperience> workExperiences;

    private List<Referee> referees;

    private Collection<UserRole> userRoles;
}
