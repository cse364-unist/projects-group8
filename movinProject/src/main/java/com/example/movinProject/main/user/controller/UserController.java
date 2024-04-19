package com.example.movinProject.main.user.controller;

import com.example.movinProject.main.user.dto.UserDto;
import com.example.movinProject.main.user.dto.UserLoginRequest;
import com.example.movinProject.main.user.dto.UserRegisterRequest;
import com.example.movinProject.main.user.service.UserService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;



    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserRegisterRequest request) {
        return userService.registerUser(request)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    // Get User Details
    /*
    @GetMapping("/my")
    public ResponseEntity<UserDto> getUserDetails(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }
        return userService.getUserDetails(authentication.getName())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    */


}