package com.teacher.configuration.authentication;


import com.teacher.userrole.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String usernameOrEmailOrMobileOrUserId;
    private String token;
    private List<UserRole> userRoles;
}
