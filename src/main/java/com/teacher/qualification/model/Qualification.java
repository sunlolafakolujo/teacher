package com.teacher.qualification.model;

import com.teacher.baseaudit.BaseAudit;
import com.teacher.contact.model.Contact;
import com.teacher.staticdata.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

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
    private String school;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(updatable = false,nullable = false)
    private LocalDate startDate;

//    @Column(updatable = false,nullable = false)
    private LocalDate endDate;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private Contact contact;
}
