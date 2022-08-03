package com.teacher.address.entity;

import com.teacher.baseaudit.BaseAudit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Contact extends BaseAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String streetNumber;

    private String StreetName;

    private String city;

    private String postZipCode;

    private String landMark;

    private String state_province;

    private String country;
}
