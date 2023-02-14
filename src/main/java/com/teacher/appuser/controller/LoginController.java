package com.teacher.appuser.controller;

import com.teacher.appuser.model.AppUser;
import com.teacher.appuser.repository.AppUserRepository;
import com.teacher.configuration.authentication.AuthRequest;
import com.teacher.configuration.authentication.AuthResponse;
import com.teacher.configuration.jwt.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api")
@AllArgsConstructor
public class LoginController {

    private final AppUserRepository appUserRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signIn")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) {

         final Authentication authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                  authRequest.getUsername(),
                  authRequest.getPassword()
              )
         );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtUtil.generateToken(authentication);
        final String username= jwtUtil.extractUsername(token);
        return ResponseEntity.ok(new AuthResponse(username, token));
    }
}
