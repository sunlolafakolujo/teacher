package com.teacher.appuser.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.teacher.contact.model.Contact;
import com.teacher.qualification.model.Qualification;
import com.teacher.reference.model.Referee;
import com.teacher.staticdata.Gender;
import com.teacher.staticdata.MeansOfIdentification;
import com.teacher.staticdata.Title;
import com.teacher.staticdata.UserType;
import com.teacher.userrole.model.UserRole;
import com.teacher.verificationtoken.model.VerificationToken;
import com.teacher.workexperience.model.WorkExperience;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Enumerated(EnumType.STRING)
    private Title title;

    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate dateOfBirth;

    private Integer age;

    private String schoolName;

    @Column(unique = true)
    private String username;

    private String password;

    @Email
    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    private String picUrl;

    private Boolean isEnabled = false;

    private String webSite;

    @Enumerated(EnumType.STRING)
    private MeansOfIdentification meansOfIdentification;

    private String meansOfIdentificationRefNumber;

    private LocalDate meansOfIdentificationIssueDate;

    private LocalDate meansOfIdentificationExpiryDate;

    private String rcNumber;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @Fetch(value=FetchMode.SELECT)
    private Collection<Contact> contacts=new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @Fetch(value=FetchMode.SELECT)
    private Collection<Qualification> qualifications=new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value=FetchMode.SELECT)
    private Collection<WorkExperience> workExperiences=new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @Fetch(value=FetchMode.SELECT)
    private Collection<Referee> referees=new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value=FetchMode.SELECT)
    private Collection<UserRole> userRoles=new HashSet<>();
}
