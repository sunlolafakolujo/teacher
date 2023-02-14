package com.teacher.appparent.service;

import com.teacher.appuser.exception.AppUserNotFoundException;
import com.teacher.appuser.model.AppUser;
import com.teacher.appuser.repository.AppUserRepository;
import com.teacher.appparent.exception.ParentNotFoundException;
import com.teacher.appparent.model.Parent;
import com.teacher.appparent.repository.ParentRepository;
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
public class ParentServiceImpl implements ParentService {
    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public Parent addParent(Parent parent) throws AppUserNotFoundException, UserRoleNotFoundException {
        Optional<AppUser> appUser=appUserRepository.findByUsernameOrEmailOrMobile(parent.getAppUser().getUsername(),
                parent.getAppUser().getEmail(),parent.getAppUser().getMobile(),parent.getAppUser().getUserId());
        if (!validatePhoneNumber(parent)){
            throw new AppUserNotFoundException("Mobile phone must be in one of these formats: " +
                    "10 or 11 digit, 0000 000 0000, 000 000 0000, 000-000-0000, 000-000-0000 ext0000");
        }if (!parent.getAppUser().getPassword().equals(parent.getAppUser().getConfirmPassword())){
            throw new AppUserNotFoundException("Password does not match");
        }if (appUser.isPresent()){
            throw new AppUserNotFoundException("Username or Email or Mobile already exist");
        }
        parent.setParentId("PAR".concat(String.valueOf(new Random().nextInt(100000))));
        parent.getAppUser().setUserId("USER".concat(String.valueOf(new Random().nextInt(100000))));
        parent.getAppUser().setUserType(UserType.PARENT);
        parent.getAppUser().setPassword(passwordEncoder.encode(parent.getAppUser().getPassword()));
        UserRole userRole=userRoleRepository.findByRoleName("PARENT")
                .orElseThrow(()->new UserRoleNotFoundException("Role Not Found"));
        List<UserRole> userRoles=new ArrayList<>();
        userRoles.add(userRole);
        parent.getAppUser().setUserRoles(userRoles);
        return parentRepository.save(parent);
    }

    @Override
    public Parent fetchParentById(Long id) throws ParentNotFoundException {
        return parentRepository.findById(id)
                .orElseThrow(()->new ParentNotFoundException("Parent ID "+id+" Not Found"));
    }

    @Override
    public Parent fetchByParentId(String parentId) throws ParentNotFoundException {
        return parentRepository.findByParentId(parentId)
                .orElseThrow(()->new ParentNotFoundException("Parent "+parentId+" Not Found"));
    }

    @Override
    public Parent fetchParentByUsernameOrEmailOrMobileOrUserId(String searchKey) throws AppUserNotFoundException {
        AppUser appUser=appUserRepository.findByUsernameOrEmailOrMobile(searchKey,searchKey,searchKey,searchKey)
                .orElseThrow(()->new AppUserNotFoundException("User "+searchKey+" Not Found"));
        return parentRepository.findByAppUser(appUser);
    }

    @Override
    public List<Parent> fetchAllParentOrByFirstOrLastName(String searchKey, Integer pageNumber) {
        Pageable pageable= PageRequest.of(pageNumber,10);
        if (searchKey.equals("")){
            return parentRepository.findAll(pageable).toList();
        }else {
            return parentRepository.findByFirstNameOrLastName(searchKey,searchKey,pageable);
        }
    }

    @Override
    public Parent updateParent(Parent parent, Long id) throws ParentNotFoundException {
        Parent savedParent=parentRepository.findById(id)
                .orElseThrow(()->new ParentNotFoundException("Parent "+id+" Not Found"));
        if (Objects.nonNull(parent.getProfession()) && !"".equals(parent.getProfession())){
            savedParent.setProfession(parent.getProfession());
        }if (Objects.nonNull(parent.getAppUser().getContact()) && !"".equals(parent.getAppUser().getContact())){
            savedParent.getAppUser().setContact(parent.getAppUser().getContact());
        }
        return parentRepository.save(savedParent);
    }

    @Override
    public void deleteParentById(Long id) throws ParentNotFoundException {
        if (parentRepository.existsById(id)){
            parentRepository.deleteById(id);
        }else {
            throw new ParentNotFoundException("Parent ID "+id+" Not Found");
        }
    }

    @Override
    public void deleteAllParent() {
        parentRepository.deleteAll();
    }

    private static boolean validatePhoneNumber(Parent parent) {
        // validate phone numbers of format "1234567890"
        if (parent.getAppUser().getMobile().matches("\\d{10}")) {
            return true;
        }
        // validate phone numbers of format "12345678901"
        else if (parent.getAppUser().getMobile().matches("\\d{11}")) {
            return true;
        }
        // validating phone number with -, . or spaces
        else if (parent.getAppUser().getMobile().matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) {
            return true;
        }
        // validating phone number with extension length from 3 to 5
        else if (parent.getAppUser().getMobile().matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) {
            return true;
        }
        // validating phone number where area code is in braces ()
        else if (parent.getAppUser().getMobile().matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) {
            return true;
        }    // Validation for India numbers
        else if (parent.getAppUser().getMobile().matches("\\d{4}[-\\.\\s]\\d{3}[-\\.\\s]\\d{3}")) {
            return true;
        } else if (parent.getAppUser().getMobile().matches("\\(\\d{5}\\)-\\d{3}-\\d{3}")) {
            return true;
        } else if (parent.getAppUser().getMobile().matches("\\(\\d{4}\\)-\\d{3}-\\d{3}")){
            return true;
        }    // return false if nothing matches the input
        else {
            return false;
        }
    }
}
