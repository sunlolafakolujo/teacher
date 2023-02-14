package com.teacher.appuser.service;

import com.teacher.appuser.exception.AppUserNotFoundException;
import com.teacher.appuser.model.AppUser;
import com.teacher.appuser.repository.AppUserRepository;
import com.teacher.staticdata.UserType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
class AppUserServiceImplTest {
    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AppUserService appUserService=new AppUserServiceImpl();

    AppUser appUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        appUser=new AppUser();
    }

    @Test
    void testThatYouCanMockUserRegistrationMethod() throws AppUserNotFoundException {
        when(appUserRepository.save(appUser)).thenReturn(appUser);
        appUserService.userRegistration(appUser);
        ArgumentCaptor<AppUser> appUserArgumentCaptor=ArgumentCaptor.forClass(AppUser.class);
        verify(appUserRepository, times(1)).save(appUserArgumentCaptor.capture());
        AppUser capturedUser=appUserArgumentCaptor.getValue();
        assertEquals(capturedUser, appUser);
    }

    @Test
    void testThatYouCanMockFindUserByIdMethod() throws AppUserNotFoundException {
        Long id=2L;
        when(appUserRepository.findById(id)).thenReturn(Optional.of(appUser));
        appUserService.findUserById(id);
        verify(appUserRepository, times(1)).findById(id);
    }

    @Test
    void testThatYouCanMockFindUserByEmailMethod() throws AppUserNotFoundException {
        String searchKey="ao@gmail.com";
        when(appUserRepository.findByUsernameOrEmailOrMobile(searchKey,searchKey,searchKey,searchKey))
                .thenReturn(Optional.of(appUser));
        appUserService.findByUsernameOrEmailOrMobileOrUserId(searchKey);
        verify(appUserRepository, times(1)).findByUsernameOrEmailOrMobile(searchKey,searchKey,
                searchKey,searchKey);
    }
    @Test
    void testThatYouCanMockFindByUserTypeMethod() throws AppUserNotFoundException {
        UserType userType=UserType.TEACHER;
        List<AppUser> appUsers=new ArrayList<>();
        Pageable pageable=PageRequest.of(0, 10);
        when(appUserRepository.findByUserType(userType, pageable)).thenReturn(appUsers);
        appUserService.findByUserType(userType, pageable);
        verify(appUserRepository, times(1)).findByUserType(userType, pageable);
    }

    @Test
    void testThatYouCanMockFindAllUsersMethod() {
        List<AppUser>appUsers=new ArrayList<>();
        Page<AppUser> appUserPage=new PageImpl<>(appUsers);
        Pageable pageable=PageRequest.of(0, 10);
        when(appUserRepository.findAll(pageable)).thenReturn(appUserPage);
        appUserService.findAllUsers(pageable);
        verify(appUserRepository, times(1)).findAll(pageable);
    }

    @Test
    void testThatYouCanMockCountUserMethod(){
        Long numberOfUsers=3L;
        when(appUserRepository.count()).thenReturn(numberOfUsers);
        appUserService.countUsers();
        verify(appUserRepository, times(1)).count();
    }

    @Test
    void testThatYouCanMockUpdateUserMethod() throws AppUserNotFoundException {
        Long id=1L;
        when(appUserRepository.findById(id)).thenReturn(Optional.of(appUser));
        appUserService.updateUser(appUser, id);
        verify(appUserRepository, times(1)).save(appUser);
    }

    @Test
    void testThatYouCanMockDeleteUserByIdMethod() throws AppUserNotFoundException {
        Long id=2L;
        doNothing().when(appUserRepository).deleteById(id);
        appUserService.deleteUserById(id);
        verify(appUserRepository, times(1)).deleteById(id);
    }

    @Test
    void testThatYouCanMockDeleteAllUsersMethod() {
        doNothing().when(appUserRepository).deleteAll();
        appUserService.deleteAllUsers();
        verify(appUserRepository, times(1)).deleteAll();
    }
}