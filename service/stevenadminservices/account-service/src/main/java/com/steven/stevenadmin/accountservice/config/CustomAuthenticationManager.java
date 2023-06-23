package com.steven.stevenadmin.accountservice.config;

import com.steven.stevenadmin.accountservice.utils.TokenUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;


import java.time.Instant;
import java.util.Date;

public class CustomAuthenticationManager implements AuthenticationManager {


    private final JwtDecoder jwtDecoder;

    public CustomAuthenticationManager(JwtDecoder decoder) {
        jwtDecoder = decoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Object credentials = authentication.getCredentials();
        String token = credentials.toString();
        Jwt decode;

        System.out.println("%%%%%%%%%%%%%%%%%%%GET-HERE%%%%%%%%%5");
        try {
            decode = jwtDecoder.decode(token);
        } catch (Exception e) {
            System.out.println("decode failed ==> "+e.getMessage());
            System.out.println("decode failed caused ==> "+e.getCause());
            authentication.setAuthenticated(false);
            return authentication;
        }

        Instant expiresAt = decode.getExpiresAt();
        assert expiresAt != null;
        if (expiresAt.isBefore(new Date().toInstant())) {
            System.out.println("token expired");
            authentication.setAuthenticated(false);
            return authentication;
        }
        authentication.setAuthenticated(true);
        return authentication;
    }
}
