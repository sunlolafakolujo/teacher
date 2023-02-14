package com.teacher.appuser.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teacher.contact.model.Contact;
import com.teacher.reference.model.Referee;
import com.teacher.staticdata.Gender;
import com.teacher.staticdata.MeansOfIdentification;
import com.teacher.staticdata.Title;
import com.teacher.staticdata.UserType;
import com.teacher.userrole.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParentDto {

    @JsonIgnore
    private Long id;

    private String userId;
}
