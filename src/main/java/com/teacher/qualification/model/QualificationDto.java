package com.teacher.qualification.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private String school;

    private LocalDate startDate;

    private LocalDate endDate;

    private String streetNumber;

    private String streetName;

    private String city;

    private String stateProvince;

    private String country;
}
