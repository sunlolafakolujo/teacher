package com.teacher.appteacher.service;

import com.teacher.appparent.model.Parent;
import com.teacher.appteacher.exception.TeacherNotFoundException;
import com.teacher.appteacher.model.Teacher;
import com.teacher.appteacher.repository.TeacherRepository;
import com.teacher.appuser.exception.AppUserNotFoundException;
import com.teacher.appuser.model.AppUser;
import com.teacher.appuser.repository.AppUserRepository;
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
public class TeacherServiceImpl implements TeacherService{
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Teacher addTeacher(Teacher teacher) throws TeacherNotFoundException, AppUserNotFoundException, UserRoleNotFoundException {
        Optional<AppUser> appUser=appUserRepository.findByUsernameOrEmailOrMobile(teacher.getAppUser().getUsername(),
                teacher.getAppUser().getUserId(),teacher.getAppUser().getMobile(),teacher.getAppUser().getEmail());
        if (appUser.isPresent()){
            throw new TeacherNotFoundException("Username or Email or Mobile already exist");
        }if (!teacher.getAppUser().getPassword().equals(teacher.getAppUser().getConfirmPassword())){
            throw new AppUserNotFoundException("Password do not match");
        }if (!validatePhoneNumber(teacher)){
            throw new AppUserNotFoundException("Mobile phone must be in one of these formats: " +
                    "10 or 11 digit, 0000 000 0000, 000 000 0000, 000-000-0000, 000-000-0000 ext0000");
        }
        teacher.setTeacherCode("TCH".concat(String.valueOf(new Random().nextInt(100000))));
        teacher.getAppUser().setUserId("USER".concat(String.valueOf(new Random().nextInt(100000))));
        teacher.getAppUser().setPassword(passwordEncoder.encode(teacher.getAppUser().getPassword()));
        UserRole userRole=userRoleRepository.findByRoleName("TEACHER")
                .orElseThrow(()->new UserRoleNotFoundException("Role Not Found"));
        List<UserRole> userRoles=new ArrayList<>();
        userRoles.add(userRole);
        teacher.getAppUser().setUserRoles(userRoles);
        teacher.getAppUser().setUserType(UserType.TEACHER);
        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher fetchById(Long id) throws TeacherNotFoundException {
        return teacherRepository.findById(id)
                .orElseThrow(()->new TeacherNotFoundException("Teacher ID "+id+" Not Found"));
    }

    @Override
    public Teacher fetchByTeacherCode(String teacherCode) throws TeacherNotFoundException {
        return teacherRepository.findByTeacherId(teacherCode)
                .orElseThrow(()->new TeacherNotFoundException("Teacher code "+teacherCode+" Not Found"));
    }

    @Override
    public List<Teacher> fetchAllTeachersOrByFirstOrLastName(String searchKey, Integer pageNumber) {
        Pageable pageable= PageRequest.of(pageNumber,10);
        if (searchKey.equals("")){
            return teacherRepository.findAll(pageable).toList();
        }else {
            return teacherRepository.findByFirstOrLastName(searchKey,searchKey,pageable);
        }
    }

    @Override
    public Teacher updateTeacher(Teacher teacher, Long id) throws TeacherNotFoundException {
        Teacher savedTeacher=teacherRepository.findById(id)
                .orElseThrow(()->new TeacherNotFoundException("Teacher ID "+id+" Not Found"));
        if (Objects.nonNull(teacher.getAppUser().getContact()) && !"".equals(teacher.getAppUser().getContact())){
            savedTeacher.getAppUser().setContact(teacher.getAppUser().getContact());
        }
        return teacherRepository.save(savedTeacher);
    }

    @Override
    public void deleteTeacherById(Long id) throws TeacherNotFoundException {
        if (teacherRepository.existsById(id)){
            teacherRepository.deleteById(id);
        }else {
            throw new TeacherNotFoundException("Teacher ID "+id+" Not Found");
        }
    }

    @Override
    public void deleteAllTeacher() {
        teacherRepository.deleteAll();
    }

    private static boolean validatePhoneNumber(Teacher teacher) {
        // validate phone numbers of format "1234567890"
        if (teacher.getAppUser().getMobile().matches("\\d{10}")) {
            return true;
        }
        // validate phone numbers of format "12345678901"
        else if (teacher.getAppUser().getMobile().matches("\\d{11}")) {
            return true;
        }
        // validating phone number with -, . or spaces
        else if (teacher.getAppUser().getMobile().matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) {
            return true;
        }
        // validating phone number with extension length from 3 to 5
        else if (teacher.getAppUser().getMobile().matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) {
            return true;
        }
        // validating phone number where area code is in braces ()
        else if (teacher.getAppUser().getMobile().matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) {
            return true;
        }    // Validation for India numbers
        else if (teacher.getAppUser().getMobile().matches("\\d{4}[-\\.\\s]\\d{3}[-\\.\\s]\\d{3}")) {
            return true;
        } else if (teacher.getAppUser().getMobile().matches("\\(\\d{5}\\)-\\d{3}-\\d{3}")) {
            return true;
        } else if (teacher.getAppUser().getMobile().matches("\\(\\d{4}\\)-\\d{3}-\\d{3}")){
            return true;
        }    // return false if nothing matches the input
        else {
            return false;
        }
    }
}
