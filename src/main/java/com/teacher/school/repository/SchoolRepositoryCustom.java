package com.teacher.school.repository;

import com.teacher.appuser.model.AppUser;
import com.teacher.school.model.School;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SchoolRepositoryCustom {
    @Query("FROM School s WHERE s.schoolId=?1 OR s.schoolName=?2 OR s.rcNumber=?3")
    Optional<School> findBySchoolIdOrNameOrRcNumber(String key1, String key2, String key3);

    @Query("FROM School s WHERE s.appUser=?1")
    School findByEmailOrUsernameOrPhoneOrUserId(AppUser appUser);
}
