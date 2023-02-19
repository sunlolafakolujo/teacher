package com.teacher.qualification.model;

import com.teacher.appuser.model.AppUser;
import com.teacher.contact.model.Contact;
import com.teacher.image.model.Image;
import com.teacher.staticdata.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewQualification {

    private String subject;
    private String degreeTitle;
    private String classOfDegree;
    private String institutionName;
    private Status currentQualification;
    private LocalDate startDate;
    private LocalDate endDate;
    private String institutionAddress;
    private AppUser appUser;
    private List<Image> certificates;
}
