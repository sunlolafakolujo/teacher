package com.teacher.employee.model;

import com.teacher.appuser.model.AppUser;
import com.teacher.image.model.Image;
import com.teacher.staticdata.Gender;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String employeeId;
    private String firstName;
    private String lastName;
    private String otherName;
    private LocalDate dateOfBirth;
    private Integer age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private AppUser appUser;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Image image;
}
