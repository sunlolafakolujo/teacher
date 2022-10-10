package com.teacher.userrole.controller;

import com.teacher.userrole.exception.UserRoleNotFoundException;
import com.teacher.userrole.model.NewUserRole;
import com.teacher.userrole.model.UpdateRole;
import com.teacher.userrole.model.UserRole;
import com.teacher.userrole.model.UserRoleDto;
import com.teacher.userrole.service.UserRoleService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/userRole")
@AllArgsConstructor
public class UserRoleController {
    private final UserRoleService userRoleService;
    private final ModelMapper modelMapper;

    @PostMapping("/saveRole")
    public ResponseEntity<NewUserRole> saveRole(@RequestBody NewUserRole newUserRole) throws UserRoleNotFoundException {
        UserRole userRole=modelMapper.map(newUserRole, UserRole.class);
        UserRole post=userRoleService.saveUserRole(userRole);
        NewUserRole posted=modelMapper.map(post, NewUserRole.class);
        return new ResponseEntity<>(posted, HttpStatus.CREATED);
    }

    @GetMapping("/findRoleById/{id}")
    public ResponseEntity<UserRoleDto> getRoleById(@PathVariable(value = "id") Long id) throws UserRoleNotFoundException {
        UserRole userRole=userRoleService.findRoleById(id);
        UserRoleDto userRoleDto=convertRoleToDto(userRole);
        return new ResponseEntity<>(userRoleDto, HttpStatus.OK);
    }

    @GetMapping("/findRoleByName/{roleName}")
    public ResponseEntity<UserRoleDto> getRoleByName(@PathVariable(value = "roleName") String roleName)
                                                                      throws UserRoleNotFoundException {
        UserRole userRole= userRoleService.findUserRoleByName(roleName);
        UserRoleDto userRoleDto=convertRoleToDto(userRole);
        return new ResponseEntity<>(userRoleDto, HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<UserRoleDto>> getAllRoles(){
       Pageable pageable= PageRequest.of(0, 10);
       return new ResponseEntity<>(userRoleService.findAllRoles(pageable)
               .stream()
               .map(this::convertRoleToDto)
               .collect(Collectors.toList()),HttpStatus.OK);
    }

    @PutMapping("/updateRole/{id}")
    public ResponseEntity<UpdateRole> updateRole(@RequestBody UpdateRole updateRole,
                                                 @PathVariable(value = "id") Long id) throws UserRoleNotFoundException {
        UserRole userRole=modelMapper.map(updateRole, UserRole.class);
        UserRole post=userRoleService.updateRole(userRole, id);
        UpdateRole posted=modelMapper.map(post, UpdateRole.class);
        return new ResponseEntity<>(posted, HttpStatus.OK);
    }

    @DeleteMapping("/deleteRole/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable(value = "id") Long id) throws UserRoleNotFoundException {
        userRoleService.deleteRoleById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteAllRoles")
    public ResponseEntity<?> deleteAll(){
        userRoleService.deleteAllRoles();
        return ResponseEntity.noContent().build();
    }


    private UserRoleDto convertRoleToDto(UserRole userRole){
        UserRoleDto userRoleDto=new UserRoleDto();
        userRoleDto.setRoleName(userRole.getRoleName());
        return userRoleDto;
    }
}
