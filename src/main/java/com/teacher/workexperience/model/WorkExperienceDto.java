package com.teacher.workexperience.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teacher.staticdata.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkExperienceDto {

    @JsonIgnore
    private Long id;

    private String company;

    private String position;

    private Status currentWork;

    private LocalDate startDate;

    private LocalDate endDate;

    private String appUserFirstName;

    private String appUserLastName;

    private String appUserEmail;

    private String appUserPhone;
}
