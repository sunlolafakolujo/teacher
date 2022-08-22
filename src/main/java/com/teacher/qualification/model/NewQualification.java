package com.teacher.qualification.model;

import com.teacher.contact.model.Contact;
import com.teacher.staticdata.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewQualification {

    private String subject;

    private String degreeTitle;

    private String classOfDegree;

    private String schoolName;

    private Status currentQualification;

    private LocalDate startDate;

    private LocalDate endDate;

    private Contact contact;
}
