package com.teacher.parent.service;

import com.teacher.appuser.exception.AppUserNotFoundException;
import com.teacher.parent.exception.ParentNotFoundException;
import com.teacher.parent.model.Parent;
import com.teacher.userrole.exception.UserRoleNotFoundException;

import java.util.List;

public interface ParentService {
    Parent addParent(Parent parent) throws AppUserNotFoundException, UserRoleNotFoundException;
    Parent fetchParentById(Long id) throws ParentNotFoundException;
    Parent fetchByParentId(String parentId) throws ParentNotFoundException;
    Parent fetchParentByUsernameOrEmailOrMobileOrUserId(String searchKey) throws AppUserNotFoundException;
    List<Parent> fetchAllParentOrByFirstOrLastName(String searchKey, Integer pageNumber);
    Parent fetchParentByAppUser(String searchKey) throws AppUserNotFoundException;
    Parent updateParent(Parent parent,Long id) throws ParentNotFoundException;
    void deleteParentById(Long id) throws ParentNotFoundException;
    void deleteAllParent();
}
