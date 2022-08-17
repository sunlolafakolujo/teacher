package com.teacher.appuser.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDto {

    @JsonIgnore
    private Long id;

    private String username;

    private String password;

    private String email;

    private String phone;
}
