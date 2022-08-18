package com.teacher.resetpassword.model;

import lombok.Data;

@Data
public class  PasswordModel {
    private String username;
    private String oldPassword;
    private String newPassword;
}
