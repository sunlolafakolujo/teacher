package com.teacher.appuser.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teacher.contact.model.Contact;
import com.teacher.staticdata.Gender;
import com.teacher.staticdata.Title;
import com.teacher.staticdata.UserType;
import com.teacher.userrole.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolDto {

    @JsonIgnore
    private Long id;

    private UserType userType;

    private String userId;

    private Title title;

    private Gender gender;

    private String schoolName;

    private LocalDate dateOfBirth;

    private Integer age;

    private String username;

    private String password;

//    private String matchingPassword;

    private String email;

    private String phone;

    private String rcNumber;

    private String picUrl;

    private String webSite;

    private Collection<Contact> contacts=new ArrayList<>();

    private Collection<UserRole> userRoles=new HashSet<>();

}
