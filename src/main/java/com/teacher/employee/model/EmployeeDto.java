package com.teacher.employee.model;

import com.teacher.image.model.Image;
import com.teacher.staticdata.Gender;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private Long id;
    private String employeeId;
    private String firstName;
    private String lastName;
    private String otherName;
    private LocalDate dateOfBirth;
    private Integer age;
    private Gender gender;
    private String email;
    private String mobile;
    private String houseNumber;
    private String streetNumber;
    private String city;
    private String landmark;
    private String stateProvince;
    private String country;
    private Image image;
}
