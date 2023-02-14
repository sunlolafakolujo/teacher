package com.teacher.meansofidentification.model;

import com.teacher.image.model.Image;
import com.teacher.staticdata.MeansOfIdentification;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class MeanOfIdentification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MeansOfIdentification meansOfIdentification;
    private String meansOfIdentificationRefNumber;
    private LocalDate meansOfIdentificationIssueDate;
    private LocalDate meansOfIdentificationExpiryDate;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Image image;
}
