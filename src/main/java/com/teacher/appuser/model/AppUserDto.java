package com.teacher.appuser.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teacher.contact.model.Contact;
import com.teacher.qualification.model.Qualification;
import com.teacher.reference.model.Referee;
import com.teacher.staticdata.Gender;
import com.teacher.staticdata.MeansOfIdentification;
import com.teacher.staticdata.Title;
import com.teacher.staticdata.UserType;
import com.teacher.userrole.model.UserRole;
import com.teacher.workexperience.model.WorkExperience;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDto {

    @JsonIgnore
    private Long id;

    private String userId;

    private UserType userType;
    private String username;
    private String email;
    private String mobile;
    private String houseNumber;
    private String streetName;
    private String city;
    private String landmark;
    private String stateProvince;
    private String country;
    private Collection<UserRole> userRoles;
}
