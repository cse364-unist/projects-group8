package com.example.movinProject.config.SpringSecurity.controller;

import com.example.movinProject.config.SpringSecurity.service.JwtService;
import com.example.movinProject.domain.user.domain.User;
import com.example.movinProject.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerTest {

    private String token;

    @Autowired
    private JwtService jwtService;

    @LocalServerPort
    private int port;
    private final TestRestTemplate restTemplate;
    HttpHeaders headers;

    @Autowired
    AuthControllerTest(TestRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        userRepository.deleteAll();
    }

    void generateToken() {
        User user1 = User.create("admin2", passwordEncoder.encode("admin2"), "admin2");
        userRepository.save(user1);

        URI targetUrl = UriComponentsBuilder.fromUriString("http://localhost:" + port + "/auth/v1/login")
                .queryParam("userName", "admin2")
                .queryParam("password", "admin2")
                .build()
                .encode()
                .toUri();

        ResponseEntity<Map<String, String>> response = restTemplate.exchange(
                targetUrl,
                HttpMethod.POST,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<Map<String, String>>() {});

        token = response.getBody().get("token");
    }

    @Test
    void authRequest() throws Exception {
        User user1 = User.create("admin", passwordEncoder.encode("admin"), "admin");
        userRepository.save(user1);

        URI targetUrl = UriComponentsBuilder.fromUriString("http://localhost:" + port + "/auth/v1/login")
                .queryParam("userName", "admin")
                .queryParam("password", "admin")
                .build()
                .encode()
                .toUri();

        ResponseEntity<Map<String, String>> response = restTemplate.exchange(
                targetUrl,
                HttpMethod.POST,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<Map<String, String>>() {});

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody().get("token"));
    }

    @Test
    void authRequest2() throws Exception {
        URI targetUrl = UriComponentsBuilder.fromUriString("http://localhost:" + port + "/auth/v1/login")
                .queryParam("userName", "string")
                .queryParam("password", "string")
                .build()
                .encode()
                .toUri();

        ResponseEntity<Map<String, String>> response = restTemplate.exchange(
                targetUrl,
                HttpMethod.POST,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<Map<String, String>>() {});

        assertEquals(403, response.getStatusCodeValue());
    }

    @Test
    void testProtectedEndpointWithValidToken() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MjUxNjIzOTAyMn0.dBjftJeZ4CVP-mB92K27uhbUJU1p1r_wW1gFWFOEjXk"; // 예시 토큰

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(token);

        URI targetUrl = UriComponentsBuilder.fromUriString("http://localhost:" + port + "/users/my")
                .build()
                .encode()
                .toUri();

        ResponseEntity<String> response = restTemplate.exchange(
                targetUrl,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void testProtectedEndpointWithValidToken3() {
        generateToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(token);

        URI targetUrl = UriComponentsBuilder.fromUriString("http://localhost:" + port + "/users/my")
                .build()
                .encode()
                .toUri();

        ResponseEntity<String> response = restTemplate.exchange(
                targetUrl,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );
        System.out.println(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
