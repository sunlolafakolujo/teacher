//package com.teacher.security.appuserdetails;
//
//import com.teacher.appuser.repository.AppUserRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@Transactional
//@AllArgsConstructor
//public class AppUserDetailService implements UserDetailsService {
//    private final AppUserRepository appUserRepository;
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        var u =appUserRepository.findUserByUsernameOptional(username);
//
//        return u.map(AppUserDetails::new)
//                .orElseThrow(()->new UsernameNotFoundException("User "+u+" Not Found"));
//    }
//}
