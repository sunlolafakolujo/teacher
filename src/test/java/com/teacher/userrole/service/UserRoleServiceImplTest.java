package com.teacher.userrole.service;

import com.teacher.userrole.exception.UserRoleNotFoundException;
import com.teacher.userrole.model.UserRole;
import com.teacher.userrole.repository.UserRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
@Transactional
class UserRoleServiceImplTest {
    @Mock
    private UserRoleRepository userRoleRepository;

    @InjectMocks
    private UserRoleService userRoleService=new UserRoleServiceImpl();

    UserRole userRole;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userRole=new UserRole();
    }

    @Test
    void testThatYouCanMockSaveUserRoleMethod() throws UserRoleNotFoundException {
        when(userRoleRepository.save(userRole)).thenReturn(userRole);
        userRoleService.saveUserRole(userRole);
        ArgumentCaptor<UserRole> userRoleArgumentCaptor=ArgumentCaptor.forClass(UserRole.class);
        Mockito.verify(userRoleRepository, times(1)).save(userRoleArgumentCaptor.capture());
        UserRole capturedUserRole=userRoleArgumentCaptor.getValue();
        assertEquals(capturedUserRole, userRole);
    }

    @Test
    void testThatYouCanMockFindRoleByIdMethod() throws UserRoleNotFoundException {
        Long id=3L;
        when(userRoleRepository.findById(id)).thenReturn(Optional.of(userRole));
        userRoleService.findRoleById(id);
        verify(userRoleRepository, times(1)).findById(id);
    }

    @Test
    void testThatYouCanMockFindUserRoleByNameMethod() throws UserRoleNotFoundException {
        String role="TEACHER";
        when(userRoleRepository.findByRoleName(role)).thenReturn(Optional.of(userRole));
        userRoleService.findUserRoleByName(role);
        verify(userRoleRepository, times(1)).findByRoleName(role);
    }

    @Test
    void testThatYouCanMockFindAllRolesMethod() {
        List<UserRole> userRoles=new ArrayList<>();
        Pageable pageable= PageRequest.of(0, 10);
        Page<UserRole> userRolePage=new PageImpl<>(userRoles);
        when(userRoleRepository.findAll(pageable)).thenReturn(userRolePage);
        userRoleService.findAllRoles(pageable);
        verify(userRoleRepository, times(1)).findAll(pageable);
    }

    @Test
    @Rollback(value = false)
    void testThatYouCanMockUpdateRoleMethod() throws UserRoleNotFoundException {
        Long id=4L;
        when(userRoleRepository.findById(id)).thenReturn(Optional.of(userRole));
        userRoleService.updateRole(userRole, id);
        verify(userRoleRepository, times(1)).save(userRole);
    }

    @Test
    void testThatYouCanMockDeleteRoleByIdMethod() throws UserRoleNotFoundException {
        Long id=3L;
        doNothing().when(userRoleRepository).deleteById(id);
        userRoleService.deleteRoleById(id);
        verify(userRoleRepository, times(1)).deleteById(id);
    }

    @Test
    void testThatYouCanMockDeleteAllRolesMethod() {
        doNothing().when(userRoleRepository).deleteAll();
        userRoleService.deleteAllRoles();
        verify(userRoleRepository, times(1)).deleteAll();
    }
}