package com.teacher.password.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class  PasswordModel {
    private String usernameOrEmailOrMobileOrUserId;
    private String token;
    private String oldPassword;
    private String newPassword;
}
