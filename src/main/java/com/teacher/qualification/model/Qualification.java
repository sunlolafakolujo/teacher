package com.teacher.qualification.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.teacher.appuser.model.AppUser;
import com.teacher.baseaudit.BaseAudit;
import com.teacher.contact.model.Contact;
import com.teacher.image.model.Image;
import com.teacher.staticdata.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.engine.internal.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Qualification extends BaseAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    private String subject;

    @Column(updatable = false)
    private String degreeTitle;

    @Column(updatable = false)
    private String classOfDegree;

    @Column(updatable = false)
    private String institutionName;

    @Enumerated(EnumType.STRING)
    private Status currentQualification;

    @Column(updatable = false,nullable = false)
    private LocalDate startDate;
    private LocalDate endDate;
    private String institutionAddress;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Image> certificates;
}
