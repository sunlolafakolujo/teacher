package com.teacher.appuser.model;

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

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
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

    @NotBlank(message = "Username is required")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @Email
    @Column(unique = true)
    @NotBlank(message = "Email is required")
    private String email;

    @Column(unique = true)
    @NotBlank(message = "Phone is required")
    private String phone;

    private String picUrl;

    private Boolean enabled= false;

    private String webSite;

    @Enumerated(EnumType.STRING)
    private MeansOfIdentification meansOfIdentification;

    private LocalDate meansOfIdentificationIssueDate;

    private LocalDate meansOfIdentificationExpiryDate;

    private String rcNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_CONTACT"))
    private Contact contact;

    @OneToMany(mappedBy = "appUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Qualification> qualifications;

    @OneToMany(mappedBy = "appUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<WorkExperience> workExperiences;

    @OneToMany(mappedBy = "appUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Referee> referees;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<UserRole> userRoles=new ArrayList<>();
}
