package com.teacher.applicationform.model;

import com.teacher.appteacher.model.Teacher;
import com.teacher.image.model.Image;
import com.teacher.vacancy.model.Vacancy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewApplicationForm {

    private String applicationId;
    private String jobTitle;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String location;
    private List<Image> resumeAndCoverLetter;
    private String messageToApplicant;
    private Teacher teacher;
    private Vacancy vacancy;
}
