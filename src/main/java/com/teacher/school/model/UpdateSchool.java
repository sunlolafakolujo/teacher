package com.teacher.school.model;

import com.teacher.appuser.model.AppUser;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSchool {
    private Long id;
    private AppUser appUser;
}
