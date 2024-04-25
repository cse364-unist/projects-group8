package com.example.movinProject.config.SpringSecurity.filter;

import com.example.movinProject.config.SpringSecurity.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;

import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void doFilterInternal_WithValidToke() throws ServletException, IOException {
        // Setup
        when(request.getHeader("Authorization")).thenReturn("Bearer validjwt");
        when(jwtService.extractUsername("validjwt")).thenReturn("user");
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername("user")).thenReturn(userDetails);
        when(jwtService.isTokenValid("validjwt", userDetails)).thenReturn(false);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Verify
        assertNull(SecurityContextHolder.getContext().getAuthentication());

    }

    @Test
    void doFilterInternal_WithValidToken2() throws ServletException, IOException {
        // Setup
        when(request.getHeader("Authorization")).thenReturn("Beax validjwt");
        when(jwtService.extractUsername("validjwt")).thenReturn("user");
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername("user")).thenReturn(userDetails);
        when(jwtService.isTokenValid("validjwt", userDetails)).thenReturn(false);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Verify
        assertNull(SecurityContextHolder.getContext().getAuthentication());

    }

    @Test
    void doFilterInternal_WithValidToken3() throws ServletException, IOException {
        // Setup
        when(request.getHeader("Authorization")).thenReturn(null);
        when(jwtService.extractUsername("validjwt")).thenReturn("user");
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername("user")).thenReturn(userDetails);
        when(jwtService.isTokenValid("validjwt", userDetails)).thenReturn(false);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Verify
        assertNull(SecurityContextHolder.getContext().getAuthentication());

    }

    @Test
    void doFilterInternal_WithValidToken4() throws ServletException, IOException {
        // Setup
        when(request.getHeader("Authorization")).thenReturn("Bearer validjwt");
        when(jwtService.extractUsername("validjwt")).thenReturn(null);
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername("user")).thenReturn(userDetails);
        when(jwtService.isTokenValid("validjwt", userDetails)).thenReturn(false);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Verify
        assertNull(SecurityContextHolder.getContext().getAuthentication());

    }

    @Test
    void doFilterInternal_WithValidToken5() throws ServletException, IOException {
        // Setup
        when(request.getHeader("Authorization")).thenReturn("Bearer validjwt");
        when(jwtService.extractUsername("validjwt")).thenReturn("user");
        UserDetails userDetails = mock(UserDetails.class);
        SecurityContextHolder.setContext(mock(SecurityContext.class));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authentication);
        when(userDetailsService.loadUserByUsername("user")).thenReturn(userDetails);
        when(jwtService.isTokenValid("validjwt", userDetails)).thenReturn(false);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);


    }
}