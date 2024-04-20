package com.example.movinProject.config.SpringSecurity.security;

import com.example.movinProject.domain.user.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class InMemoryUserDetailsService implements UserDetailsService {

    private final Map<String, User> users = new ConcurrentHashMap<>();

    public InMemoryUserDetailsService(PasswordEncoder passwordEncoder) {
        User user1 = User.create("user1",  passwordEncoder.encode("12345"), "ti");
        User user2 = User.create("user2", passwordEncoder.encode("12645"), "tis");
        users.put("user1", user1 );
        users.put("user2",user2);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(users.get(username))
                .map(this::getUser)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", username)));
    }

    private UserDetails getUser(User user) {
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUserName())
                .password(user.getPassword())
                .authorities("USER")
                .build();
    }
}