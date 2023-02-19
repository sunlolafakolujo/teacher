package com.teacher.applicationform.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teacher.image.model.Image;
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

    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String location;
    private List<Image> resumeAndCoverLetter;
    private String jobTitle;
}
