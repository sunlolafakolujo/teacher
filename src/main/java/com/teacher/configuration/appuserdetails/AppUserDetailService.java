package com.teacher.configuration.appuserdetails;

import com.teacher.appuser.exception.AppUserNotFoundException;
import com.teacher.appuser.model.AppUser;
import com.teacher.appuser.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class AppUserDetailService implements UserDetailsService {
    private final AppUserRepository appUserRepository;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String searchKey) {
        AppUser appUser=appUserRepository.findByUsernameOrEmailOrMobile(searchKey,searchKey,searchKey,searchKey)
                .orElseThrow(()->new AppUserNotFoundException("User "+searchKey+" Not Found"));
        if (appUser==null){
            log.info("User does not exist in the database");
        }
        return new org
                .springframework
                .security.core.userdetails
                .User(appUser.getUsername(),appUser.getPassword(), appUser.getIsEnabled(),true,
                                                  true, true,getAuthority(appUser));
    }

    private Set<SimpleGrantedAuthority> getAuthority(AppUser appUser) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        appUser.getUserRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        });
        return authorities;
    }
}



