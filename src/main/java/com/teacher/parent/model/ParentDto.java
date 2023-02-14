package com.teacher.parent.model;

import com.teacher.image.model.Image;
import com.teacher.meansofidentification.model.MeanOfIdentification;
import com.teacher.parentemployer.model.Employer;
import com.teacher.reference.model.Referee;
import com.teacher.staticdata.Gender;
import com.teacher.staticdata.MeansOfIdentification;
import com.teacher.staticdata.Title;
import lombok.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ParentDto {
    private Long id;
    private String parentId;
    private Title title;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String email;
    private String mobile;
    private Image image;
    private String homeNumber;
    private String streetName;
    private String city;
    private String landmark;
    private String stateProvince;
    private String country;
    private String profession;
    private MeansOfIdentification meansOfIdentification;
    private String meansOfIdentificationRefNumber;
    private LocalDate meansOfIdentificationIssueDate;
    private LocalDate meansOfIdentificationExpiryDate;
    private String employerName;
    private String employerEmail;
    private String employerPhone;
    private String employerOfficeNumber;
    private String employerOfficeStreet;
    private String employerOfficeCity;
    private String employerOfficeLandmark;
    private String employerOfficeState;
    private String employerOfficeCountry;
    private List<Referee> referees;
}
