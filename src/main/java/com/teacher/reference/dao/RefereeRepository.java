package com.teacher.reference.dao;

import com.teacher.reference.model.Referee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefereeRepository extends JpaRepository<Referee, Long> {
}
