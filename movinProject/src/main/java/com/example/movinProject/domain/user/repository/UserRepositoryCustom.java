package com.example.movinProject.domain.user.repository;

import com.example.movinProject.domain.user.domain.User;
import java.util.Optional;

public interface UserRepositoryCustom {
    Optional<User> findByUserName(String userName);
}
