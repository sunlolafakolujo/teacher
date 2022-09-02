package com.teacher.vacancy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacancyDto {

    @JsonIgnore
    private Long id;

    private String publisherName;

    private String jobId;

    private String jobTitle;

    private String jobGrade;

    private String location;

    private String keyResponsibility;

    private String skillRequirement;

    private String experienceEducation;

    private LocalDate publishedDate;

    private LocalDate closingDate;
}
