package com.teacher.appuser.repository;

import com.teacher.appuser.model.AppUser;
import com.teacher.staticdata.UserType;
import org.apache.el.parser.BooleanNode;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AppUserRepositoryCustom {

    @Query("From AppUser a Where a.username=?1 OR a.email=?2 OR a.mobile=?3 OR a.userId=?4")
    Optional<AppUser> findByUsernameOrEmailOrMobile(String key1, String key2, String key3, String key4);

    @Query("From AppUser a Where a.userType=?1")
    List<AppUser> findByUserType(UserType userType, Pageable pageable);
}
