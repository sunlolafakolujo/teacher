package com.teacher.school.model;

import com.teacher.appuser.model.AppUser;
import com.teacher.image.model.Image;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NewSchool {
    private String schoolId;
    private String schoolName;
    private LocalDate yearFounded;
    private Integer age;
    private String rcNumber;
    private String schoolWebSite;
    private AppUser appUser;
    private Image cacCertificate;
}
