package com.teacher.appuser.model;

import com.teacher.contact.model.Contact;
import com.teacher.reference.model.Referee;
import com.teacher.staticdata.MeansOfIdentification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAppUser {
    private Long id;
    private Contact contact;
}
