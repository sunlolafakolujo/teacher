package com.teacher.appparent.service;

import com.teacher.appuser.exception.AppUserNotFoundException;
import com.teacher.appparent.exception.ParentNotFoundException;
import com.teacher.appparent.model.Parent;
import com.teacher.userrole.exception.UserRoleNotFoundException;

import java.util.List;

public interface ParentService {
    Parent addParent(Parent parent) throws AppUserNotFoundException, UserRoleNotFoundException;
    Parent fetchParentById(Long id) throws ParentNotFoundException;
    Parent fetchByParentId(String parentId) throws ParentNotFoundException;
    Parent fetchParentByUsernameOrEmailOrMobileOrUserId(String searchKey) throws AppUserNotFoundException;
    List<Parent> fetchAllParentOrByFirstOrLastName(String searchKey, Integer pageNumber);
    Parent updateParent(Parent parent,Long id) throws ParentNotFoundException;
    void deleteParentById(Long id) throws ParentNotFoundException;
    void deleteAllParent();
}
