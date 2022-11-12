package com.teacher.applicationform.model;

import com.teacher.baseaudit.BaseAudit;
import com.teacher.vacancy.model.Vacancy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationForm extends BaseAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private String applicationId;

//    private String messageToApplicant;

    private String firstName;

    private String lastName;

    private String phone;

    @Email
    private String email;

    private String location;

    private String resumeUrl;

    private String coverLetterUrl;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Vacancy vacancy;
}
