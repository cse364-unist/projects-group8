package com.example.movinProject.config.SpringSecurity.service;


import com.example.movinProject.config.SpringSecurity.dto.UserLoginRequest;
import java.util.Map;

public interface AuthService {
     Map<String, String> authRequest(UserLoginRequest userLoginRequest);

}
