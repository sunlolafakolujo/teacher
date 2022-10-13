package com.teacher.vacancy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teacher.staticdata.JobType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacancyDto {

    @JsonIgnore
    private Long id;

    private String jobDetail;
}
