package com.teacher.contact.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewContact {

    private String streetNumber;

    private String streetName;

    private String city;

    private String postZipCode;

    private String landMark;

    private String stateProvince;

    private String country;
}
