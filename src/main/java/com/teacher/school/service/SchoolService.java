package com.teacher.school.service;

import com.teacher.appuser.exception.AppUserNotFoundException;
import com.teacher.school.exception.SchoolNotFoundException;
import com.teacher.school.model.School;
import com.teacher.userrole.exception.UserRoleNotFoundException;

import java.util.List;

public interface SchoolService {
    School addSchool(School school) throws SchoolNotFoundException, AppUserNotFoundException, UserRoleNotFoundException;
    School fetchById(Long id) throws SchoolNotFoundException;
    School fetchBySchoolIdOrNameOrRcNumber(String searchKey) throws SchoolNotFoundException;
    School fetchByEmailOrUsernameOrPhoneOrUserId(String searchKey) throws AppUserNotFoundException;
    List<School> fetchAllSchools(Integer pageNumber);
    School updateSchool(School school,Long id) throws SchoolNotFoundException;
    void deleteSchoolById(Long id) throws SchoolNotFoundException;
    void deleteAllSchool();
}
