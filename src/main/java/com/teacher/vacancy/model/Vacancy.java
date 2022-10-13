package com.teacher.vacancy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teacher.appuser.model.AppUser;
import com.teacher.baseaudit.BaseAudit;
import com.teacher.staticdata.JobType;
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

    private LocalDate publishedDate;

    private LocalDate closingDate;

    private String jobTitle;

    private String jobLocation;

    @Column(length = 1000000)
    private String aboutUs;

    @Enumerated(EnumType.STRING)
    private JobType jobType;

    private String jobSchedule;

    @Column(length = 10000)
    @NotBlank(message = "Job Description is required")
    private String keyResponsibility;

    @Column(length = 10000)
    private String skillRequirement;

    @Column(length = 10000)
    private String qualification;

    @Column(length = 5000)
    private String benefit;

    @Column(length = 600000)
    private String jobDetail;
}
