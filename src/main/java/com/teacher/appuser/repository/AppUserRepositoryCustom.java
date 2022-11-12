package com.teacher.appuser.repository;

import com.teacher.appuser.model.AppUser;
import com.teacher.staticdata.UserType;
import org.apache.el.parser.BooleanNode;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AppUserRepositoryCustom {

    @Query("From AppUser a Where a.username=?1")
    Optional<AppUser> findUserByUsername(String username);

    @Query("From AppUser a Where a.username=?1")
    AppUser findByUsername(String username);

    @Query("From AppUser a Where a.email=?1")
    Optional<AppUser> findUserByEmail(String email);

    @Query("From AppUser a Where a.phone=?1")
    Optional<AppUser> findUserByPhone(String phone);

    @Query("From AppUser a Where a.firstName Like %?1%")
    List<AppUser> findUserByFirstName(String firstName, Pageable pageable);

    @Query("From AppUser a Where a.lastName Like %?1%")
    List<AppUser> findUserByLastName(String lastName, Pageable pageable);

    @Query("From AppUser a Where a.schoolName Like %?1%")
    List<AppUser> findBySchoolName(String schoolName, Pageable pageable);

    @Query("From AppUser a Where a.userType=?1")
    List<AppUser> findByUserType(UserType userType, Pageable pageable);
}
