package com.teacher.qualification.model;

import com.teacher.contact.model.Contact;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Qualification {
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
    @NotBlank(message = "School is required")
    private String school;

    @OneToOne
    private Contact contact;
}
