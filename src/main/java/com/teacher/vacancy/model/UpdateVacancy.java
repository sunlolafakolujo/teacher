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
public class UpdateVacancy {

    private Long id;

    private LocalDate closingDate;

    private String jobTitle;

    private String jobLocation;

    private String aboutUs;

    private JobType jobType;

    private String jobSchedule;

    private String keyResponsibility;

    private String skillRequirement;

    private String qualification;

    private String benefit;

    private String messageToApplicant;
}
