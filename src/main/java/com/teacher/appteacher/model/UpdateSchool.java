package com.teacher.appteacher.model;

import com.teacher.appuser.model.AppUser;
import com.teacher.image.model.Image;
import com.teacher.reference.model.Referee;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSchool {
    private Long id;
    private AppUser appUser;
    private Image image;
    private List<Referee> referees;
}
