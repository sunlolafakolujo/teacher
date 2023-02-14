package com.teacher.parent.model;

import com.teacher.appuser.model.AppUser;
import com.teacher.image.model.Image;
import com.teacher.meansofidentification.model.MeanOfIdentification;
import com.teacher.parentemployer.model.Employer;
import com.teacher.reference.model.Referee;
import com.teacher.staticdata.Gender;
import com.teacher.staticdata.Title;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class NewParent {
    private String parentId;
    private Title title;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String profession;
    private MeanOfIdentification meanOfIdentification;
    private Employer employer;
    private Image image;
    private AppUser appUser;
    private Collection<Referee> referees=new ArrayList<>();
}
