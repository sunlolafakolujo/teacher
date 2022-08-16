package com.teacher.reference.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReferenceDto {

    @JsonIgnore
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String referenceLetter;
}
