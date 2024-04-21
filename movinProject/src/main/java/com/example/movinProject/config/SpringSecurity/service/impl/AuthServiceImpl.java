package com.example.movinProject.config.SpringSecurity.service.impl;


import com.example.movinProject.config.SpringSecurity.dto.UserLoginRequest;
import com.example.movinProject.config.SpringSecurity.service.AuthService;
import com.example.movinProject.config.SpringSecurity.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Override
    public Map<String, String> authRequest(UserLoginRequest userLoginRequest) {
        try {
            final var authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginRequest.getUserName(), userLoginRequest.getPassword())
            );
            final var userDetails = (UserDetails) authenticate.getPrincipal();
            return getToken(userDetails);
        } catch (AuthenticationException e) {
            log.error("Authentication error for user {}: {}", userLoginRequest.getUserName(), e.getMessage());
            throw e;
        }
    }

    public Map<String, String> getToken( UserDetails userDetails) {
        final var roles = userDetails.getAuthorities();
        final var username = userDetails.getUsername();
        final var token = jwtService.generateToken(Map.of("role", roles), username);
        return Map.of("token", token);
    }
}
