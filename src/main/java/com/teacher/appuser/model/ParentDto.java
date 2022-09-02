package com.teacher.appuser.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teacher.contact.model.Contact;
import com.teacher.reference.model.Referee;
import com.teacher.staticdata.Gender;
import com.teacher.staticdata.MeansOfIdentification;
import com.teacher.staticdata.Title;
import com.teacher.staticdata.UserType;
import com.teacher.userrole.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParentDto {

    @JsonIgnore
    private Long id;

    private String userId;

    private UserType userType;

    private Title title;

    private String firstName;

    private String lastName;

    private Gender gender;

    private String username;

    private String password;

    private String email;

    private String phone;

    private String picUrl;

    private MeansOfIdentification meansOfIdentification;

    private String meansOfIdentificationIssueDate;

    private String meansOfIdentificationExpiryDate;

    private Contact contact;

    private List<Referee> referees;

    private List<UserRole> userRoles;
}
