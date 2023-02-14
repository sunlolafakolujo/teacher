package com.teacher.configuration.authentication;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    private String username;
    private String password;
}
