package com.teacher.address.entity;

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

    private String StreetName;

    private String city;

    private String postZipCode;

    private String landMark;

    private String state_province;

    private String country;
}
