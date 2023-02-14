package com.teacher.school.model;

import com.teacher.appuser.model.AppUser;
import com.teacher.baseaudit.BaseAudit;
import com.teacher.image.model.Image;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class School extends BaseAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String schoolId;
    private String schoolName;
    private LocalDate yearFounded;
    private Integer age;
    private String rcNumber;
    private String schoolWebSite;

    @OneToOne(cascade = CascadeType.ALL)
    private AppUser appUser;

    @OneToOne(cascade = CascadeType.ALL)
    private Image cacCertificate;
}
