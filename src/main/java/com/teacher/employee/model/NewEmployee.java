package com.teacher.employee.model;

import com.teacher.appuser.model.AppUser;
import com.teacher.image.model.Image;
import com.teacher.staticdata.Gender;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NewEmployee {
    private String employeeId;
    private String firstName;
    private String lastName;
    private String otherName;
    private LocalDate dateOfBirth;
    private Integer age;
    private Gender gender;
    private AppUser appUser;
    private Image image;
}
