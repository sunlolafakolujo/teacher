package com.teacher.image.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teacher.appparent.model.Parent;
import com.teacher.appteacher.model.Teacher;
import com.teacher.meansofidentification.model.MeanOfIdentification;
import com.teacher.school.model.School;
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

    @JsonIgnore
    @ToString.Exclude
    @OneToOne(mappedBy = "cacCertificate")
    private School school;

    @JsonIgnore
    @ToString.Exclude
    @OneToOne(mappedBy = "image")
    private Parent parent;

    @JsonIgnore
    @ToString.Exclude
    @OneToOne(mappedBy = "image")
    private Teacher teacher;

}
