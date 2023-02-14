package com.teacher.configuration.authentication;


import com.teacher.appuser.model.AppUser;

public class AuthResponse {
    private final String username;
    private final String token;

    public AuthResponse(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public String getUsername() {
        return this.username;
    }

    public String getToken() {
        return this.token;
    }

}
