package com.teacher.appteacher.model;

import com.teacher.appuser.model.AppUser;
import com.teacher.image.model.Image;
import com.teacher.meansofidentification.model.MeanOfIdentification;
import com.teacher.qualification.model.Qualification;
import com.teacher.reference.model.Referee;
import com.teacher.staticdata.Gender;
import com.teacher.staticdata.Title;
import com.teacher.workexperience.model.WorkExperience;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NewTeacher {

    private String teacherCode;
    private Title title;
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate dateOfBirth;
    private AppUser appUser;
    private MeanOfIdentification meanOfIdentification;
    private Image image;
    private List<Qualification> qualifications;
    private List<WorkExperience> workExperiences;
    private List<Referee> referees;
}
