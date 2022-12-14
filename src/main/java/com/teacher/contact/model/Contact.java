package com.teacher.contact.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teacher.appuser.model.AppUser;
import com.teacher.baseaudit.BaseAudit;
import com.teacher.qualification.model.Qualification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Contact extends BaseAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Street Number can't be blank")
    private String streetNumber;

    @NotEmpty(message = "Street Name can't be blank")
    private String StreetName;

    @NotEmpty(message = "City can't be blank")
    private String city;

    private String postZipCode;

    private String landMark;

    @NotEmpty(message = "State can't be blank")
    private String stateProvince;

    @NotEmpty(message = "Country can't be blank")
    private String country;

    @JsonIgnore
    @ToString.Exclude
    @ManyToMany(mappedBy = "contacts",cascade = CascadeType.ALL)
    private Collection<AppUser> appUsers=new ArrayList<>();
}
