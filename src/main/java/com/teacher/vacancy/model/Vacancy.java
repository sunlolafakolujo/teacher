package com.teacher.vacancy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teacher.appuser.model.AppUser;
import com.teacher.baseaudit.BaseAudit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Vacancy extends BaseAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Publisher name is required")
    private String companyName;

    @NotBlank(message = "Job Id is required")
    @Column(unique = true)
    private String jobId;

    private String jobTitle;

    private String jobLocation;

    @Column(length = 400000)
    private String jobDetails;

    @JsonIgnore
    @ToString.Exclude
    @Column(length = 10000)
    @NotBlank(message = "Job Description is required")
    private String keyResponsibility;

    @Column(length = 10000)
    @JsonIgnore
    @ToString.Exclude
    private String skillRequirement;

    @JsonIgnore
    @ToString.Exclude
    @Column(length = 10000)
    private String experienceEducation;

    @JsonIgnore
    @ToString.Exclude
    @Column(length = 5000)
    private String benefit;

    private LocalDate publishedDate;

    private LocalDate closingDate;
}
