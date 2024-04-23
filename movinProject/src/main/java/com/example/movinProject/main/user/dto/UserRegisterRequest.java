package com.example.movinProject.main.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRegisterRequest {
    private String userName;
    private String password;
    private String email;

    public static UserRegisterRequest create(String newUser, String password123, String mail) {

        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.userName = newUser;
        userRegisterRequest.password = password123;
        userRegisterRequest.email = mail;

        return userRegisterRequest;
    }
}
