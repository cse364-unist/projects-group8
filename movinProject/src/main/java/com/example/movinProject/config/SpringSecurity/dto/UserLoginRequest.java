package com.example.movinProject.config.SpringSecurity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserLoginRequest {
    private String userName;
    private String password;

    public static UserLoginRequest create(String username, String password) {
        UserLoginRequest us = new UserLoginRequest();
        us.userName = username;
        us.password = password;

        return us;
    }
}
