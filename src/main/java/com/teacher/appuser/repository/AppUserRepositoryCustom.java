package com.teacher.appuser.repository;

import com.teacher.appuser.model.AppUser;
import com.teacher.staticdata.UserType;
import org.apache.el.parser.BooleanNode;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppUserRepositoryCustom {

    @Query("From AppUser a Where a.username=?1")
    AppUser findUserByUsername(String username);

    @Query("From AppUser a Where a.email=?1")
    AppUser findUserByEmail(String email);

    @Query("From AppUser a Where a.phone=?1")
    AppUser findUserByPhone(String phone);

    @Query("From AppUser a Where a.firstName Like %?#{[0].toUpperCase}%")
    List<AppUser> findUserByFirstName(String firstName, Pageable pageable);

    @Query("From AppUser a Where a.lastName Like %?#{[0].toUpperCase}%")
    List<AppUser> findUserByLastName(String lastName, Pageable pageable);

    @Query("From AppUser a Where a.schoolName Like %?#{[0].toUpperCase}%")
    List<AppUser> findBySchoolName(String schoolName);

    @Query("From AppUser a Where a.userType=?1")
    List<AppUser> findByUserType(UserType userType, Pageable pageable);
}
