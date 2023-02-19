package com.teacher.contact.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teacher.appuser.model.AppUser;
import com.teacher.baseaudit.BaseAudit;
import com.teacher.qualification.model.Qualification;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Contact extends BaseAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String houseNumber;
    private String streetName;
    private String city;
    private String landmark;
    private String stateProvince;
    private String country;

    @OneToOne(mappedBy = "contact")
    private AppUser appUser;
}
