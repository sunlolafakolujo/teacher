package com.teacher.qualification.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewQualification {

    private String subject;

    private String classOfDegree;

    private String school;
}
