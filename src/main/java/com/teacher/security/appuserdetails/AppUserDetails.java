//package com.teacher.security.appuserdetails;
//
//import com.teacher.appuser.model.AppUser;
//import com.teacher.security.userroleauthority.UserRoleAuthority;
//import lombok.AllArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.stream.Collectors;
//
//@AllArgsConstructor
//public class AppUserDetails implements UserDetails {
//    private final AppUser appUser;
//
//    @Override
//    public String getUsername() {
//        return appUser.getUsername();
//    }
//
//    @Override
//    public String getPassword() {
//        return appUser.getPassword();
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return appUser.getUserRoles()
//                .stream()
//                .map(UserRoleAuthority::new)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return appUser.getIsEnabled();
//    }
//}
