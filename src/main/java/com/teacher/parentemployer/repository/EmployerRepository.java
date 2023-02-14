package com.teacher.parentemployer.repository;

import com.teacher.parentemployer.model.Employer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployerRepository extends JpaRepository<Employer, Long> {
}
