package com.teacher.appuser.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAppUser {

    private Long id;

    private String username;

    private String password;

    private String email;

    private String phone;
}
