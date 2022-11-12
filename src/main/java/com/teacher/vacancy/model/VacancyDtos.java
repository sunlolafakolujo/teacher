package com.teacher.vacancy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teacher.staticdata.JobType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacancyDtos {

    @JsonIgnore
    private Long id;

//    private String recruiterName;

    private String jobTitle;

    private String aboutUs;

    private JobType jobType;

    private String jobSchedule;

    private String jobLocation;

    private String keyResponsibility;

    private String skillRequirement;

    private String qualification;

    private LocalDate publishedDate;

    private LocalDate closingDate;

    private String recruiterName;

    private String messageToApplicant;
}
