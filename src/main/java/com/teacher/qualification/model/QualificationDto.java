package com.teacher.qualification.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private String streetNumber;

    private String streetName;

    private String city;

    private String stateProvince;

    private String country;
}
