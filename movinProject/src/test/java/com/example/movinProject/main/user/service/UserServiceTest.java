package com.example.movinProject.main.user.service;

import com.example.movinProject.domain.debateJoinedUser.repository.DebateJoinedUserRepository;
import com.example.movinProject.domain.debateRoom.domain.DebateRoom;
import com.example.movinProject.domain.debateRoom.repository.DebateRoomRepository;
import com.example.movinProject.domain.debateVote.repository.DebateVoteRepository;
import com.example.movinProject.domain.movie.repository.MovieRepository;
import com.example.movinProject.domain.user.domain.User;
import com.example.movinProject.domain.user.repository.UserRepository;
import com.example.movinProject.main.movie.service.MovieService;
import com.example.movinProject.main.user.dto.UserDto;
import com.example.movinProject.main.user.dto.UserRegisterRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserServiceTest {


    @Mock
    private UserRepository userRepository;

    @Mock
    private DebateRoomRepository debateRoomRepository;

    @Mock
    private DebateJoinedUserRepository debateJoinedUserRepository;

    @Mock
    private DebateVoteRepository debateVoteRepository;

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private MovieRepository movieRepository;


    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


        @Test
    void registerUser() {
            UserRegisterRequest request =UserRegisterRequest.create("newUser", "password123", "user@example.com");
            when(userRepository.existsByUserName(request.getUserName())).thenReturn(false);
            when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");

            User user = User.create(request.getUserName(),
            "encodedPassword",
            request.getEmail());

            when(userRepository.save(any(User.class))).thenReturn(user);

            // Act
            Optional<UserDto> result = userService.registerUser(request);

            // Assert
            assertTrue(result.isPresent());
            assertEquals(request.getUserName(), result.get().getName());
    }

    @Test
    void getUserDetails() {

        String username = "existingUser";
        User user = User.create( username, "hi", "hi2");
        List<Long> debateRoomIds = List.of(1L, 2L);

        when(userRepository.findByUserName(username)).thenReturn(Optional.of(user));
        when(debateJoinedUserRepository.findIdsByUserName(username)).thenReturn(debateRoomIds);
        when(debateRoomRepository.findAllById(debateRoomIds)).thenReturn(List.of(new DebateRoom()));

        // Act
        UserDto result = userService.getUserDetails(username);

        // Assert
        assertEquals(username, result.getName());
        assertNotNull(result.getJoinedDebateRooms());
    }
}