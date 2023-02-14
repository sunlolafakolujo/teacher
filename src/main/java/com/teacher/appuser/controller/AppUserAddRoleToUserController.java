package com.teacher.appuser.controller;

import com.teacher.appuser.exception.AppUserNotFoundException;
import com.teacher.userrole.exception.UserRoleNotFoundException;
import com.teacher.userrole.model.AddRoleToUser;
import com.teacher.userrole.service.UserRoleService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/teacher/appUser")
@AllArgsConstructor
public class AppUserAddRoleToUserController {
    private final UserRoleService userRoleService;

    @PostMapping("/addRoleToUser")
    public ResponseEntity<?> addRoleToUser(@Valid @RequestBody AddRoleToUser addRoleToUser)
                                                              throws AppUserNotFoundException, UserRoleNotFoundException {
        userRoleService.addRoleToUser(addRoleToUser.getUsername(), addRoleToUser.getRoleName());
        return ResponseEntity.ok().build();
    }
}
