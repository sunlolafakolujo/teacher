package com.teacher.employee.service;

import com.teacher.appteacher.model.Teacher;
import com.teacher.appuser.exception.AppUserNotFoundException;
import com.teacher.appuser.model.AppUser;
import com.teacher.appuser.repository.AppUserRepository;
import com.teacher.employee.exception.EmployeeNotFoundException;
import com.teacher.employee.model.Employee;
import com.teacher.employee.repository.EmployeeRepository;
import com.teacher.staticdata.UserType;
import com.teacher.userrole.exception.UserRoleNotFoundException;
import com.teacher.userrole.model.UserRole;
import com.teacher.userrole.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService{
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Employee addEmployee(Employee employee) throws AppUserNotFoundException, EmployeeNotFoundException, UserRoleNotFoundException {
        Optional<AppUser> appUser=appUserRepository.findByUsernameOrEmailOrMobile(employee.getAppUser().getUsername(),
                employee.getAppUser().getUserId(),employee.getAppUser().getMobile(),employee.getAppUser().getEmail());
        if (appUser.isPresent()){
            throw new EmployeeNotFoundException("Employee already exist");
        }if (!employee.getAppUser().getPassword().equals(employee.getAppUser().getConfirmPassword())){
            throw new AppUserNotFoundException("Password do not match");
        }if (!validatePhoneNumber(employee)){
            throw new AppUserNotFoundException("Mobile phone must be in one of these formats: " +
                    "10 or 11 digit, 0000 000 0000, 000 000 0000, 000-000-0000, 000-000-0000 ext0000");
        }
        employee.getAppUser().setPassword(passwordEncoder.encode(employee.getAppUser().getPassword()));
        employee.getAppUser().setUserId("USER".concat(String.valueOf(new Random().nextInt(100000))));
        employee.setEmployeeId("EMP".concat(String.valueOf(new Random().nextInt(100000))));
        UserRole userRole=userRoleRepository.findByRoleName("EMPLOYEE")
                .orElseThrow(()->new UserRoleNotFoundException("Role Not Found"));
        List<UserRole> userRoles=new ArrayList<>();
        userRoles.add(userRole);
        employee.getAppUser().setUserRoles(userRoles);
        employee.getAppUser().setUserType(UserType.EMPLOYEE);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee fetchById(Long id) throws EmployeeNotFoundException {
        return employeeRepository.findById(id)
                .orElseThrow(()->new EmployeeNotFoundException("Employee ID "+id+" Not Found"));
    }

    @Override
    public Employee fetchByEmployeeId(String employeeId) throws EmployeeNotFoundException {
        return employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(()->new EmployeeNotFoundException("Employee ID "+employeeId+" Not Found"));
    }

    @Override
    public List<Employee> fetchAllEmployeesOrByFirstOrLastOrOtherName(String searchKey, Integer pageNumber) {
        Pageable pageable= PageRequest.of(pageNumber,10);
        if (searchKey.equals("")){
            return employeeRepository.findAll(pageable).toList();
        }else {
            return employeeRepository.findByFirstOrLastOrOtherName(searchKey,searchKey,searchKey,pageable);
        }

    }

    @Override
    public Employee fetchByEmailOrUsernameOrMobileOrUserId(String searchKey) throws AppUserNotFoundException {
        AppUser appUser=appUserRepository.findByUsernameOrEmailOrMobile(searchKey,searchKey,searchKey,searchKey)
                .orElseThrow(()->new AppUserNotFoundException("User "+searchKey+" Not Found"));
        return employeeRepository.findByEmailOrUsernameOrMobileOrUserId(appUser);
    }

    @Override
    public Employee updateEmployee(Employee employee, Long id) throws EmployeeNotFoundException {
        Employee saveEmployee=employeeRepository.findById(id)
                .orElseThrow(()->new EmployeeNotFoundException("Employee ID "+id+" Not Found"));
        if (Objects.nonNull(employee.getFirstName()) && !"".equalsIgnoreCase(employee.getFirstName())){
            saveEmployee.setFirstName(employee.getFirstName());
        }if (Objects.nonNull(employee.getLastName()) && !"".equalsIgnoreCase(employee.getLastName())){
            saveEmployee.setLastName(employee.getLastName());
        }if (Objects.nonNull(employee.getOtherName()) && !"".equalsIgnoreCase(employee.getOtherName())){
            saveEmployee.setOtherName(employee.getOtherName());
        }if (Objects.nonNull(employee.getAppUser().getContact()) && !"".equals(employee.getAppUser().getContact())){
            saveEmployee.getAppUser().setContact(employee.getAppUser().getContact());
        }if (Objects.nonNull(employee.getImage()) && !"".equals(employee.getImage())){
            saveEmployee.setImage(employee.getImage());
        }
        return employeeRepository.save(saveEmployee);
    }

    @Override
    public void deleteEmployeeById(Long id) throws EmployeeNotFoundException {
        if (employeeRepository.existsById(id)){
            employeeRepository.deleteById(id);
        }else {
            throw new EmployeeNotFoundException("Employee ID "+id+" Not Found");
        }
    }

    @Override
    public void deleteAllEmployees() {
        employeeRepository.deleteAll();
    }

    private static boolean validatePhoneNumber(Employee employee) {
        // validate phone numbers of format "1234567890"
        if (employee.getAppUser().getMobile().matches("\\d{10}")) {
            return true;
        }
        // validate phone numbers of format "12345678901"
        else if (employee.getAppUser().getMobile().matches("\\d{11}")) {
            return true;
        }
        // validating phone number with -, . or spaces
        else if (employee.getAppUser().getMobile().matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) {
            return true;
        }
        // validating phone number with extension length from 3 to 5
        else if (employee.getAppUser().getMobile().matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) {
            return true;
        }
        // validating phone number where area code is in braces ()
        else if (employee.getAppUser().getMobile().matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) {
            return true;
        }    // Validation for India numbers
        else if (employee.getAppUser().getMobile().matches("\\d{4}[-\\.\\s]\\d{3}[-\\.\\s]\\d{3}")) {
            return true;
        } else if (employee.getAppUser().getMobile().matches("\\(\\d{5}\\)-\\d{3}-\\d{3}")) {
            return true;
        } else if (employee.getAppUser().getMobile().matches("\\(\\d{4}\\)-\\d{3}-\\d{3}")){
            return true;
        }    // return false if nothing matches the input
        else {
            return false;
        }
    }
}
