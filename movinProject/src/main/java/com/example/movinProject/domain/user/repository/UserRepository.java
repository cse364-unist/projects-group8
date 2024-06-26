package com.example.movinProject.domain.user.repository;

import com.example.movinProject.domain.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    boolean existsByUserName(String userName);

    Optional<User> findByUserName(String username);
}
