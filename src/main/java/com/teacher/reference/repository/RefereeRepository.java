package com.teacher.reference.repository;

import com.teacher.reference.model.Referee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface RefereeRepository extends JpaRepository<Referee, Long> {
}
