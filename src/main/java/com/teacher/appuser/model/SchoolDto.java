package com.teacher.appuser.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teacher.contact.model.Contact;
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
public class SchoolDto {

    @JsonIgnore
    private Long id;

    private UserType userType;

    private String schoolName;

    private String username;

    private String password;

    private String email;

    private String phone;

    private String rcNumber;

    private Contact contact;

    private List<UserRole> userRoles;

}
