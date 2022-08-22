package com.teacher.qualification.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateQualification {

    private Long id;

    private String subject;

    private String degreeTitle;

    private String classOfDegree;

    private String schoolName;
}
