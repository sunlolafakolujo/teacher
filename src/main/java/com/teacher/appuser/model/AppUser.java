package com.teacher.appuser.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teacher.contact.model.Contact;
import com.teacher.parent.model.Parent;
import com.teacher.qualification.model.Qualification;
import com.teacher.reference.model.Referee;
import com.teacher.staticdata.Gender;
import com.teacher.staticdata.MeansOfIdentification;
import com.teacher.staticdata.Title;
import com.teacher.staticdata.UserType;
import com.teacher.userrole.model.UserRole;
import com.teacher.vacancy.model.Vacancy;
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
import javax.validation.constraints.NotNull;
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

    @Column(unique = true)
    private String username;

    @Column(length = 64)
    private String password;

    @Transient
    private String confirmPassword;

    @Email
    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String mobile;
    private Boolean isEnabled = false;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Contact contact;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "app_user_user_roles",
    joinColumns = {@JoinColumn(name = "app_user_id", referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "user_role_id", referencedColumnName = "id")})
    private Collection<UserRole> userRoles=new HashSet<>();

    @JsonIgnore
    @ToString.Exclude
    @OneToOne(mappedBy = "appUser")
    private Parent parent;
}
