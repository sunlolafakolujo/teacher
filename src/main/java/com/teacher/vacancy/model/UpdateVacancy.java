package com.teacher.vacancy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateVacancy {

    private Long id;

    private String jobTitle;

    private String jobGrade;

    private String location;

    private String keyResponsibility;

    private String skillRequirement;

    private String experienceEducation;

    private LocalDate closingDate;
}
