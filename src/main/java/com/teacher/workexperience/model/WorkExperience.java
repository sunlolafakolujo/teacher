package com.teacher.workexperience.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.teacher.appuser.model.AppUser;
import com.teacher.baseaudit.BaseAudit;
import com.teacher.staticdata.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WorkExperience extends BaseAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Company required")
    @Column(updatable = false)
    private String company;

    @NotNull(message = "Company required")
    @Column(updatable = false)
    private String position;

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    private Status currentWork;

    @NotNull(message = "Start Date is required")
    @Column(nullable = false, updatable = false)
    private LocalDate startDate;

    @Column(updatable = false)
    private LocalDate endDate;

    @JsonIgnore
    @ToString.Exclude
    @ManyToMany(mappedBy = "workExperiences")
    private Collection<AppUser> appUsers=new HashSet<>();
}
