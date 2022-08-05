package com.teacher.address.entity;

import com.teacher.baseaudit.BaseAudit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


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
}
