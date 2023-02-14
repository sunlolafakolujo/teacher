package com.teacher.parent.repository;

import com.teacher.parent.model.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentRepository extends JpaRepository<Parent,Long>,ParentRepositoryCustom {
}
