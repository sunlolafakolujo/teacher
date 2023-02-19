package com.teacher.employee.repository;

import com.teacher.employee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long>,EmployeeRepositoryCustom {
}
