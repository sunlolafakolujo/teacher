package com.teacher.vacancy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Vacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Job Id is required")
    private String jobId;

    private String jobTitle;

    private String jobGrade;

    private String location;

    @Column(length = 10000)
    @NotBlank(message = "Job Description is required")
    private String keyResponsibility;

    @Column(length = 10000)
    private String skillRequirement;

    @Column(length = 10000)
    private String experienceEducation;

    @NotBlank(message = "Published date is required")
    private LocalDate publishedDate;

    @NotBlank(message = "Closed date is required")
    private LocalDate closedDate;
}
