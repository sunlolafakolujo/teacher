package com.teacher.vacancy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teacher.applicationform.model.ApplicationForm;
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
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Vacancy extends BaseAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Recruiter name is required")
    private String recruiterName;

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

    @Column(length = 10000)
    private String messageToApplicant;

    @Column(length = 600000)
    private String jobDetail;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(nullable = false)
    private AppUser appUser;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "vacancy",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    List<ApplicationForm> applicationForms=new ArrayList<>();
}
