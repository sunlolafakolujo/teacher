package com.teacher.appuser.model;

import com.teacher.contact.model.Contact;
import com.teacher.reference.model.Referee;
import com.teacher.staticdata.MeansOfIdentification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAppUser {

    private Long id;

    private String firstName;

    private String lastName;

    private String schoolName;

    private String email;

    private String phone;

    private String picUrl;

    private String webSite;

    private MeansOfIdentification meansOfIdentification;

    private String meansOfIdentificationIssueDate;

    private String meansOfIdentificationExpiryDate;

    private Collection<Contact> contacts=new ArrayList<>();

    private Collection<Referee> referees=new ArrayList<>();
}
