package com.teacher.appteacher.model;

import com.teacher.appuser.model.AppUser;
import com.teacher.baseaudit.BaseAudit;
import com.teacher.image.model.Image;
import com.teacher.meansofidentification.model.MeanOfIdentification;
import com.teacher.qualification.model.Qualification;
import com.teacher.reference.model.Referee;
import com.teacher.staticdata.Gender;
import com.teacher.staticdata.Title;
import com.teacher.workexperience.model.WorkExperience;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Teacher extends BaseAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String teacherCode;
    private Title title;
    private String firstName;
    private String lastName;
    private String otherName;
    private Gender gender;
    private LocalDate dateOfBirth;
    private Integer age;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private AppUser appUser;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private MeanOfIdentification meanOfIdentification;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Image image;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    private List<Qualification> qualifications;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    private List<WorkExperience> workExperiences;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    private List<Referee> referees;
}
