package com.teacher.userrole.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teacher.appuser.model.AppUser;
import com.teacher.baseaudit.BaseAudit;
import com.teacher.staticdata.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRole extends BaseAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String roleName;
    @JsonIgnore
    @ToString.Exclude
    @ManyToMany(mappedBy = "userRoles", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<AppUser> appUsers;

}
