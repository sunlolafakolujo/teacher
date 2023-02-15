package com.teacher.appteacher.repository;

import com.teacher.appteacher.model.Teacher;
import com.teacher.appuser.model.AppUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TeacherRepositoryCustom {
    @Query("FROM Teacher t WHERE t.teacherId=?1")
    Optional<Teacher> findByTeacherId(String teacherId);

    @Query("FROM Teacher t WHERE t.firstName=?1 OR t.lastName=?2")
    List<Teacher> findByFirstOrLastName(String key1, String key2, Pageable pageable);

    @Query("FROM Teacher t WHERE t.appUser=?1")
    Teacher findTeacherByEmailOrUsernameOrPhoneOrUserId(AppUser appUser);
}
