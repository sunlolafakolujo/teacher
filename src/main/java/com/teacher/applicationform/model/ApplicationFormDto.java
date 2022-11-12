package com.teacher.applicationform.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teacher.vacancy.model.Vacancy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationFormDto {

    @JsonIgnore
    private Long id;

//    private String applicationId;

    private String firstName;

    private String lastName;

    private String phone;

    private String email;

    private String location;//Selection

    private String resumeUrl;

    private String coverLetterUrl;

    private String jobTitle;

    private String username;
}
