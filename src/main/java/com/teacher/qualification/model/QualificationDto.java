package com.teacher.qualification.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teacher.appuser.model.AppUser;
import com.teacher.staticdata.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QualificationDto {

    @JsonIgnore
    private Long id;

    private String subject;

    private String degreeTitle;

    private String classOfDegree;

    private String institutionName;

    private Status currentQualification;

    private LocalDate startDate;

    private LocalDate endDate;

    private String institutionAddress;

    private AppUser appUser;
}
