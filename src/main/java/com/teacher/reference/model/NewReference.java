package com.teacher.reference.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewReference {

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String referenceLetter;
}
