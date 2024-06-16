package com.example.movinProject.config.SpringSecurity.controller;

import com.example.movinProject.config.SpringSecurity.dto.UserLoginRequest;
import com.example.movinProject.config.SpringSecurity.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth/v1")
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> authRequest(
            @RequestParam String userName,
            @RequestParam String password){
        UserLoginRequest userLoginRequest = UserLoginRequest.create(userName, password);
        log.info("AuthResource.authRequest start {}", userLoginRequest);
        var userRegistrationResponse = authService.authRequest(userLoginRequest);
        log.info("AuthResource.authRequest end {}", userRegistrationResponse);
        return new ResponseEntity<>(userRegistrationResponse, HttpStatus.OK);
    }


}
