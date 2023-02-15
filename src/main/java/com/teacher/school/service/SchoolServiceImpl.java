package com.teacher.school.service;

import com.teacher.appparent.model.Parent;
import com.teacher.appuser.exception.AppUserNotFoundException;
import com.teacher.appuser.model.AppUser;
import com.teacher.appuser.repository.AppUserRepository;
import com.teacher.school.exception.SchoolNotFoundException;
import com.teacher.school.model.School;
import com.teacher.school.repository.SchoolRepository;
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

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Transactional
@Service
public class SchoolServiceImpl implements SchoolService{
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public School addSchool(School school) throws SchoolNotFoundException, AppUserNotFoundException, UserRoleNotFoundException {
        Optional<AppUser> appUser=appUserRepository.findByUsernameOrEmailOrMobile(school.getAppUser().getUsername(),
                school.getAppUser().getUserId(),school.getAppUser().getMobile(),school.getAppUser().getEmail());
        if (appUser.isPresent()){
            throw new SchoolNotFoundException("School already exist");
        }if (!school.getAppUser().getPassword().equals(school.getAppUser().getConfirmPassword())){
            throw new AppUserNotFoundException("Password do not match");
        }if (!validatePhoneNumber(school)){
            throw new AppUserNotFoundException("Mobile phone must be in one of these formats: " +
                    "10 or 11 digit, 0000 000 0000, 000 000 0000, 000-000-0000, 000-000-0000 ext0000");
        }
        school.getAppUser().setUserId("USER".concat(String.valueOf(new Random().nextInt(100000))));
        school.getAppUser().setPassword(passwordEncoder.encode(school.getAppUser().getPassword()));
        school.getAppUser().setUserType(UserType.SCHOOL);
        school.setAge(Period.between(school.getYearFounded(), LocalDate.now()).getYears());
        UserRole userRole=userRoleRepository.findByRoleName("SCHOOL").orElseThrow(()->new UserRoleNotFoundException("Role Not Found"));
        List<UserRole> userRoles=new ArrayList<>();
        userRoles.add(userRole);
        school.getAppUser().setUserRoles(userRoles);
        return schoolRepository.save(school);
    }

    @Override
    public School fetchById(Long id) throws SchoolNotFoundException {
        return schoolRepository.findById(id)
                .orElseThrow(()->new SchoolNotFoundException("School with ID "+id+" Not Found"));
    }

    @Override
    public School fetchBySchoolIdOrNameOrRcNumber(String searchKey) throws SchoolNotFoundException {
        return schoolRepository.findBySchoolIdOrNameOrRcNumber(searchKey,searchKey,searchKey)
                .orElseThrow(()->new SchoolNotFoundException("School with ID "+searchKey+" Not Found"));
    }

    @Override
    public School fetchByEmailOrUsernameOrPhoneOrUserId(String searchKey) throws AppUserNotFoundException {
        AppUser appUser=appUserRepository.findByUsernameOrEmailOrMobile(searchKey,
                searchKey,searchKey,searchKey)
                .orElseThrow(()->new AppUserNotFoundException("User "+searchKey+" Not Found"));
        return schoolRepository.findByEmailOrUsernameOrPhoneOrUserId(appUser);
    }

    @Override
    public List<School> fetchAllSchools(Integer pageNumber) {
        Pageable pageable= PageRequest.of(pageNumber,10);
        return schoolRepository.findAll(pageable).toList();
    }

    @Override
    public School updateSchool(School school, Long id) throws SchoolNotFoundException {
        School savedSchool=schoolRepository.findById(id)
                .orElseThrow(()->new SchoolNotFoundException("School with ID "+id+" Not Found"));
        if (Objects.nonNull(school.getAppUser().getMobile()) && !"".equalsIgnoreCase(school.getAppUser().getMobile())){
            savedSchool.getAppUser().setMobile(school.getAppUser().getMobile());
        }if (Objects.nonNull(school.getAppUser().getContact()) && !"".equals(school.getAppUser().getContact())){
            savedSchool.getAppUser().setContact(school.getAppUser().getContact());
        }
        return schoolRepository.save(savedSchool);
    }

    @Override
    public void deleteSchoolById(Long id) throws SchoolNotFoundException {
        if (schoolRepository.existsById(id)){
            schoolRepository.deleteById(id);
        }else {
            throw new SchoolNotFoundException("School ID "+id+" Not Found");
        }
    }

    @Override
    public void deleteAllSchool() {
        schoolRepository.deleteAll();
    }

    private static boolean validatePhoneNumber(School school) {
        // validate phone numbers of format "1234567890"
        if (school.getAppUser().getMobile().matches("\\d{10}")) {
            return true;
        }
        // validate phone numbers of format "12345678901"
        else if (school.getAppUser().getMobile().matches("\\d{11}")) {
            return true;
        }
        // validating phone number with -, . or spaces
        else if (school.getAppUser().getMobile().matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) {
            return true;
        }
        // validating phone number with extension length from 3 to 5
        else if (school.getAppUser().getMobile().matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) {
            return true;
        }
        // validating phone number where area code is in braces ()
        else if (school.getAppUser().getMobile().matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) {
            return true;
        }    // Validation for India numbers
        else if (school.getAppUser().getMobile().matches("\\d{4}[-\\.\\s]\\d{3}[-\\.\\s]\\d{3}")) {
            return true;
        } else if (school.getAppUser().getMobile().matches("\\(\\d{5}\\)-\\d{3}-\\d{3}")) {
            return true;
        } else if (school.getAppUser().getMobile().matches("\\(\\d{4}\\)-\\d{3}-\\d{3}")){
            return true;
        }    // return false if nothing matches the input
        else {
            return false;
        }
    }
}
