package com.teacher.appuser.repository;

import com.teacher.appuser.model.AppUser;
import org.apache.el.parser.BooleanNode;
import org.springframework.data.jpa.repository.Query;

public interface AppUserRepositoryCustom {

    @Query("From AppUser a Where a.username=?1")
    AppUser findUserByUsername(String username);

    @Query("From AppUser a Where a.email=?1")
    AppUser findUserByEmail(String email);

    @Query("From AppUser a Where a.phone=?1")
    AppUser findUserByPhone(String phone);

    @Query("From AppUser a Where a.enable=?1")
    AppUser findUserByStatus(Boolean enable);

}
