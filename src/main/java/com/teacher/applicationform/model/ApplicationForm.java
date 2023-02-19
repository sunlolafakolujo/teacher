package com.teacher.applicationform.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teacher.appteacher.model.Teacher;
import com.teacher.appuser.model.AppUser;
import com.teacher.baseaudit.BaseAudit;
import com.teacher.image.model.Image;
import com.teacher.vacancy.model.Vacancy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    private String location;
    private String messageToApplicant;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Teacher teacher;

    @JsonIgnore
    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Vacancy vacancy;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Image> resumeAndCoverLetter;
}
