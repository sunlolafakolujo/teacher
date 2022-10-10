package com.teacher.qualification.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.teacher.appuser.model.AppUser;
import com.teacher.baseaudit.BaseAudit;
import com.teacher.contact.model.Contact;
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
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Qualification extends BaseAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    @NotBlank(message = "Subject is required")
    private String subject;

    @Column(updatable = false)
    @NotBlank(message = "Degree title is required")
    private String degreeTitle;

    @Column(updatable = false)
    @NotBlank(message = "Class of degree is required")
    private String classOfDegree;

    @Column(updatable = false)
    private String institutionName;

    @Enumerated(EnumType.STRING)
    private Status currentQualification;

    @Column(updatable = false,nullable = false)
    private LocalDate startDate;

    private LocalDate endDate;

    private String institutionAddress;

    @JsonIgnore
    @ToString.Exclude
    @ManyToMany(mappedBy = "qualifications")
    private Collection<AppUser> appUsers=new HashSet<>();
}
