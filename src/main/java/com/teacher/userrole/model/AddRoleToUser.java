package com.teacher.userrole.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddRoleToUser {
    private String username;
    private String roleName;
}
