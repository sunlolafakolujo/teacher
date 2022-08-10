package com.teacher.qualification.model;

import com.teacher.contact.model.Contact;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewQualification {

    private String subject;

    private String degreeTitle;

    private String classOfDegree;

    private String school;

    private Contact contact;
}
