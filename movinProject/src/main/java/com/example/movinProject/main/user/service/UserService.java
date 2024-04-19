package com.example.movinProject.main.user.service;

import com.example.movinProject.domain.user.domain.User;
import com.example.movinProject.domain.user.repository.UserRepository;
import com.example.movinProject.main.user.dto.UserDto;
import com.example.movinProject.main.user.dto.UserRegisterRequest;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public Optional<UserDto> loginUser(String userName, String password) {
        Optional<User> user = userRepository.findByUserName(userName);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
                        return Optional.of(UserDto.builder()
                    .id(user.get().getId())
                    .name(user.get().getUserName())
                    .money(user.get().getMoney())
                    .lastAttendance(user.get().getLastAttendance())
                    .build());
        }
        return Optional.empty();
    }

    @Transactional
    public Optional<UserDto> registerUser(UserRegisterRequest request) {
        if (userRepository.existsByUserName(request.getUserName())) {
            return Optional.empty();
        }
        User newUser = User.create(request.getUserName(), request.getPassword(),request.getEmail());
        userRepository.save(newUser);
        return Optional.of(UserDto.builder()
                .id(newUser.getId())
                .name(newUser.getUserName())
                        .money(0)
                        .lastAttendance(LocalDateTime.of(2024, 4, 1, 0, 0))
                .build());
    }

    public Optional<UserDto> getUserDetails(String userName) {
        return userRepository.findByUserName(userName)
                .map(user -> UserDto.builder()
                        .id(user.getId())
                        .name(user.getUserName())
                        .money(user.getMoney())
                        .lastAttendance(user.getLastAttendance())
                        .build());
    }

}