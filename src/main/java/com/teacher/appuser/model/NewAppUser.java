package com.teacher.appuser.model;

import com.teacher.contact.model.Contact;
import com.teacher.staticdata.UserType;
import com.teacher.userrole.model.UserRole;
import lombok.*;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NewAppUser {
    private String userId;
    private UserType userType;
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private String mobile;
    private Contact contact;
    private List<UserRole> userRoles;
}
