package com.teacher.appuser.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewAppUser {

    private String username;

    private String password;

    private String matchingPassword;

    private String email;

    private String phone;
}
