package com.teacher.school.model;

import com.teacher.image.model.Image;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SchoolDto {
    private Long id;
    private String schoolId;
    private String schoolName;
    private LocalDate yearFounded;
    private Integer age;
    private String rcNumber;
    private String schoolWebSite;
    private String email;
    private String phone;
    private String streetNumber;
    private String streetName;
    private String city;
    private String landmark;
    private String stateProvince;
    private String country;
    private Image cacCertificate;
}
