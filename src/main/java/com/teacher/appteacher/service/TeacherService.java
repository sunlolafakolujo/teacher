package com.teacher.appteacher.service;

import com.teacher.appteacher.exception.TeacherNotFoundException;
import com.teacher.appteacher.model.Teacher;
import com.teacher.appuser.exception.AppUserNotFoundException;
import com.teacher.userrole.exception.UserRoleNotFoundException;

import java.util.List;

public interface TeacherService {
    Teacher addTeacher(Teacher teacher) throws TeacherNotFoundException, AppUserNotFoundException, UserRoleNotFoundException;
    Teacher fetchById(Long id) throws TeacherNotFoundException;
    Teacher fetchByTeacherCode(String teacherCode) throws TeacherNotFoundException;
    List<Teacher> fetchAllTeachersOrByFirstOrLastName(String searchKey, Integer pageNumber);
    Teacher updateTeacher(Teacher teacher,Long id) throws TeacherNotFoundException;
    void deleteTeacherById(Long id) throws TeacherNotFoundException;
    void deleteAllTeacher();
}
