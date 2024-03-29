package com.teacher.parentemployer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teacher.contact.model.Contact;
import com.teacher.appparent.model.Parent;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Employer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String employerName;
    private String employerEmail;
    private String employerMobile;
    private String employerWebsite;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Contact employerAddress;

    @JsonIgnore
    @ToString.Exclude
    @OneToOne(mappedBy = "employer")
    private Parent parent;
}
