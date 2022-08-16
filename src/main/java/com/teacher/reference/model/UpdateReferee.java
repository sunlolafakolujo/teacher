package com.teacher.reference.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateReferee {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String referenceLetter;
}
