package com.teacher.appparent.model;

import com.teacher.appuser.model.AppUser;
import com.teacher.baseaudit.BaseAudit;
import com.teacher.image.model.Image;
import com.teacher.meansofidentification.model.MeanOfIdentification;
import com.teacher.parentemployer.model.Employer;
import com.teacher.reference.model.Referee;
import com.teacher.staticdata.Gender;
import com.teacher.staticdata.Title;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Parent extends BaseAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String parentId;
    private Title title;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String profession;

    @OneToOne(cascade = CascadeType.ALL)
    private AppUser appUser;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private MeanOfIdentification meanOfIdentification;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Employer employer;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Image image;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Referee> referees=new ArrayList<>();
}
