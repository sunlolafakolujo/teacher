package com.teacher.appuser.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teacher.contact.model.Contact;
import com.teacher.appparent.model.Parent;
import com.teacher.staticdata.UserType;
import com.teacher.userrole.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Collection;
import java.util.HashSet;

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
