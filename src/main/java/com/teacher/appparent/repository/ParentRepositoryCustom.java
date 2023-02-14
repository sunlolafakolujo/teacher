package com.teacher.appparent.repository;

import com.teacher.appuser.model.AppUser;
import com.teacher.appparent.model.Parent;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ParentRepositoryCustom {
    @Query("FROM Parent p WHERE p.parentId=?1")
    Optional<Parent> findByParentId(String parentId);

    @Query("FROM Parent p WHERE p.firstName=?1 OR p.lastName=?2")
    List<Parent> findByFirstNameOrLastName(String key1, String key2, Pageable pageable);

    @Query("FROM Parent p WHERE p.appUser=?1")
    Parent findByAppUser(AppUser appUser);
}
