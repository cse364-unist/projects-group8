package com.example.movinProject.main.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequest {
    private String userName;
    private String password;
    private String email;
}
