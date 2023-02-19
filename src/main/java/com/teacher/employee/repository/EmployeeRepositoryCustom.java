package com.teacher.employee.repository;

import com.teacher.appuser.model.AppUser;
import com.teacher.employee.model.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepositoryCustom {
    @Query("FROM Employee e WHERE e.employeeId=?1")
    Optional<Employee> findByEmployeeId(String employeeId);

    @Query("FROM Employee e WHERE e.firstName=?1 OR e.lastName=?2 OR e.otherName=?3")
    List<Employee> findByFirstOrLastOrOtherName(String key1, String key2, String key3, Pageable pageable);

    @Query("FROM Employee e WHERE e.appUser=?2")
    Employee findByEmailOrUsernameOrMobileOrUserId(AppUser appUser);
}
