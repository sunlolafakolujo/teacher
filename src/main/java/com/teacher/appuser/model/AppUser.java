package com.teacher.appuser.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teacher.applicationform.model.ApplicationForm;
import com.teacher.appteacher.model.Teacher;
import com.teacher.contact.model.Contact;
import com.teacher.appparent.model.Parent;
import com.teacher.staticdata.UserType;
import com.teacher.userrole.model.UserRole;
import com.teacher.vacancy.model.Vacancy;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
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
    private List<UserRole> userRoles=new ArrayList<>();

    @JsonIgnore
    @ToString.Exclude
    @OneToOne(mappedBy = "appUser")
    private Parent parent;

    @JsonIgnore
    @ToString.Exclude
    @OneToOne(mappedBy = "appUser")
    private Teacher teacher;

    @JsonIgnore
    @ToString.Exclude
    @OneToOne(mappedBy = "appUser")
    private Vacancy vacancy;

}
