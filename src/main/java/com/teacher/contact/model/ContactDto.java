package com.teacher.contact.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDto {

    @JsonIgnore
    private Long id;

    private String streetNumber;

    private String streetName;

    private String city;

    private String postZipCode;

    private String landMark;

    private String stateProvince;

    private String country;
}
