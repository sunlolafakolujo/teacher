package com.teacher.parent.repository;

import com.teacher.appuser.model.AppUser;
import com.teacher.parent.model.Parent;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ParentRepositoryCustom {
    @Query("From Parent p Where p.parentId=?1")
    Optional<Parent> findByParentId(String parentId);

    @Query("FROM Parent p WHERE p.firstName=?1 OR p.lastName=?2")
    List<Parent> findByFirstNameOrLastName(String key1, String key2, Pageable pageable);

    @Query("FROM Parent p. WHERE p.appUser=?1")
    Parent findByAppUser(AppUser appUser);
}
