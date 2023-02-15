package com.teacher.appteacher.repository;

import com.teacher.appteacher.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher,Long>, TeacherRepositoryCustom {
}
