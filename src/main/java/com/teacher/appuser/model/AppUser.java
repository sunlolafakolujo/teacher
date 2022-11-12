package com.teacher.appuser.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teacher.contact.model.Contact;
import com.teacher.qualification.model.Qualification;
import com.teacher.reference.model.Referee;
import com.teacher.staticdata.Gender;
import com.teacher.staticdata.MeansOfIdentification;
import com.teacher.staticdata.Title;
import com.teacher.staticdata.UserType;
import com.teacher.userrole.model.UserRole;
import com.teacher.vacancy.model.Vacancy;
import com.teacher.validator.email.ValidEmail;
import com.teacher.validator.password.PasswordMatches;
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
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
//@PasswordMatches
//@ValidEmail
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
    @NotBlank(message = "Username is taken")
    private String username;

    @Column(length = 64)
    private String password;

//    private String matchingPassword;

    @Email
    @NotBlank(message = "Email is taken")
    @Column(unique = true)
    private String email;

    @Column(unique = true)
    @NotBlank(message = "Phone is taken")
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

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vacancy> vacancies;
}
