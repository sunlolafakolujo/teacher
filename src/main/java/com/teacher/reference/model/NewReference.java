package com.teacher.reference.model;

import com.teacher.image.model.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewReference {

    private String refereeCode;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private List<Image> referenceLetter;
}
