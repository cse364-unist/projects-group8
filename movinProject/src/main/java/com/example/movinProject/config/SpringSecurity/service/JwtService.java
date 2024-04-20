package com.example.movinProject.config.SpringSecurity.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface JwtService {
    boolean isTokenValid(String token, UserDetails userDetails);
    String extractUsername(String token);
    String generateToken(Map<String, Object> extraClaims, String username);

}
