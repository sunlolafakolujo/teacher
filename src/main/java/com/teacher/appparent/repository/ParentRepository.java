package com.teacher.appparent.repository;

import com.teacher.appparent.model.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentRepository extends JpaRepository<Parent,Long>,ParentRepositoryCustom {
}
