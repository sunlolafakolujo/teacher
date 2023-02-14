package com.teacher.image.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teacher.meansofidentification.model.MeanOfIdentification;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String imageType;

    @Column(length = 10000000)
    private byte[] picByte;

    public Image(String name, String imageType, byte[] picByte) {
        this.name = name;
        this.imageType = imageType;
        this.picByte = picByte;
    }

    @JsonIgnore
    @ToString.Exclude
    @OneToOne(mappedBy = "image")
    private MeanOfIdentification meanOfIdentification;
}
