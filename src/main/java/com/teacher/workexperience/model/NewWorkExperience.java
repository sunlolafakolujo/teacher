package com.teacher.workexperience.model;

import com.teacher.staticdata.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewWorkExperience {

    private String company;

    private String position;

    private Status current;

    private LocalDate startDate;

    private LocalDate endDate;
}
