package com.example.movinProject.config.SpringSecurity.service.impl;

import com.example.movinProject.config.SpringSecurity.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

class JwtServiceImplTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetails userDetails;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(userDetails.getUsername()).thenReturn("user");
    }

   /*@Test
    void whenUsernameDoesNotMatch_thenAuthenticationFails() {
        String invalidToken = "invalid.jwt.token";
        when(jwtService.extractUsername(invalidToken)).thenReturn("wronguser");
        when(jwtService.isTokenExpired(invalidToken)).thenReturn(false);

        boolean result = jwtService.isTokenValid(invalidToken, userDetails);

        assertFalse(result);
    }

    @Test
    void whenTokenIsExpired_thenAuthenticationFails() {
        String expiredToken = "expired.jwt.token";
        when(jwtService.extractUsername(expiredToken)).thenReturn("user");
        when(jwtService.isTokenExpired(expiredToken)).thenReturn(true);

        boolean result = jwtService.isTokenValid(expiredToken, userDetails);

        assertFalse(result);
    }"*/
}