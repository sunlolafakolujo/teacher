package com.teacher.employee.service;

import com.teacher.appuser.exception.AppUserNotFoundException;
import com.teacher.employee.exception.EmployeeNotFoundException;
import com.teacher.employee.model.Employee;
import com.teacher.userrole.exception.UserRoleNotFoundException;

import java.util.List;

public interface EmployeeService {
    Employee addEmployee(Employee employee) throws AppUserNotFoundException, EmployeeNotFoundException, UserRoleNotFoundException;
    Employee fetchById(Long id) throws EmployeeNotFoundException;
    Employee fetchByEmployeeId(String employeeId) throws EmployeeNotFoundException;
    List<Employee> fetchAllEmployeesOrByFirstOrLastOrOtherName(String searchKey, Integer pageNumber);
    Employee fetchByEmailOrUsernameOrMobileOrUserId(String searchKey) throws AppUserNotFoundException;
    Employee updateEmployee(Employee employee,Long id) throws EmployeeNotFoundException;
    void deleteEmployeeById(Long id) throws EmployeeNotFoundException;
    void deleteAllEmployees();
}
