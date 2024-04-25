package com.example.movinProject.main.user.service;

import com.example.movinProject.domain.debateJoinedUser.domain.DebateJoinedUser;
import com.example.movinProject.domain.debateJoinedUser.repository.DebateJoinedUserRepository;
import com.example.movinProject.domain.debateRoom.domain.DebateRoom;
import com.example.movinProject.domain.debateRoom.model.StateType;
import com.example.movinProject.domain.debateRoom.repository.DebateRoomRepository;
import com.example.movinProject.domain.debateVote.domain.DebateVote;
import com.example.movinProject.domain.debateVote.repository.DebateVoteRepository;
import com.example.movinProject.domain.movie.domain.Movie;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    void getUserDetailsWhenUserExists() {
        // Arrange
        String username = "existingUser";
        User user = User.create(username, "encodedPassword", "user@example.com");
        List<Long> debateRoomIds = List.of(1L, 2L);
        DebateRoom debateRoom = new DebateRoom();
        Movie movie = new Movie();

        when(userRepository.findByUserName(username)).thenReturn(Optional.of(user));
        when(debateJoinedUserRepository.findIdsByUserName(username)).thenReturn(debateRoomIds);
        when(debateRoomRepository.findAllById(debateRoomIds)).thenReturn(List.of(debateRoom));
        when(movieRepository.findById(any())).thenReturn(Optional.of(movie));
        when(debateVoteRepository.findByUserNameAndDebateRoomId(username, 1L)).thenReturn(new DebateVote());

        // Act
        UserDto result = userService.getUserDetails(username);

        // Assert
        assertNotNull(result);
        assertEquals(username, result.getName());
        assertFalse(result.getJoinedDebateRooms().isEmpty());
    }

    @Test
    void getUserDetailsWhenUserExists2() {
        // Arrange
        String username = "existingUser";
        User user = User.create(username, "encodedPassword", "user@example.com");
        List<Long> debateRoomIds = List.of(1L, 2L);
        DebateRoom debateRoom = DebateRoom.initTest(1L,"title1", "topic", StateType.OPEN, LocalDateTime.of(2024,1,1,1,1), 1L);
        Movie movie = new Movie();

        when(userRepository.findByUserName(username)).thenReturn(Optional.of(user));
        when(debateJoinedUserRepository.findIdsByUserName(username)).thenReturn(debateRoomIds);
        when(debateRoomRepository.findAllById(debateRoomIds)).thenReturn(List.of(debateRoom));
        when(movieRepository.findById(any())).thenReturn(Optional.of(movie));
        DebateVote debateVote = DebateVote.create(1L, "existingUser", true, LocalDateTime.of(2024,1,1,1,1));

        when(debateVoteRepository.findByUserNameAndDebateRoomId(username, 1L)).thenReturn(debateVote);



        DebateJoinedUser debateJoinedUser = DebateJoinedUser.create(1L, "existingUser", true);
        when(debateJoinedUserRepository.findByUserNameAndDebateRoomId(username, 1L)).thenReturn(Optional.of(debateJoinedUser));
        // Act
        UserDto result = userService.getUserDetails(username);

        // Assert
        assertNotNull(result);
        assertEquals(username, result.getName());
        assertFalse(result.getJoinedDebateRooms().isEmpty());
    }

    @Test
    void getUserDetailsWhenUserExists3() {
        // Arrange
        String username = "existingUser";
        User user = User.create(username, "encodedPassword", "user@example.com");
        List<Long> debateRoomIds = List.of(1L, 2L);
        DebateRoom debateRoom = DebateRoom.initTest(1L,"title1", "topic", StateType.OPEN, LocalDateTime.of(2024,1,1,1,1), 1L);
        Movie movie = new Movie();

        when(userRepository.findByUserName(username)).thenReturn(Optional.of(user));
        when(debateJoinedUserRepository.findIdsByUserName(username)).thenReturn(debateRoomIds);
        when(debateRoomRepository.findAllById(debateRoomIds)).thenReturn(List.of(debateRoom));
        when(movieRepository.findById(any())).thenReturn(Optional.of(movie));
        DebateVote debateVote = DebateVote.create(1L, "existingUser", false, LocalDateTime.of(2024,1,1,1,1));

        when(debateVoteRepository.findByUserNameAndDebateRoomId(username, 1L)).thenReturn(debateVote);



        DebateJoinedUser debateJoinedUser = DebateJoinedUser.create(1L, "existingUser", false);
        when(debateJoinedUserRepository.findByUserNameAndDebateRoomId(username, 1L)).thenReturn(Optional.of(debateJoinedUser));
        // Act
        UserDto result = userService.getUserDetails(username);

        // Assert
        assertNotNull(result);
        assertEquals(username, result.getName());
        assertFalse(result.getJoinedDebateRooms().isEmpty());
    }

    @Test
    void getUserDetailsWhenUserExistsWithDisagreements() {
        // Arrange
        String username = "existingUser";
        User user = User.create(username, "encodedPassword", "user@example.com");
        Long debateRoomId = 1L;

        DebateRoom debateRoom = new DebateRoom();
        debateRoom.setId(debateRoomId);

        Movie movie = new Movie();

        DebateJoinedUser disagreeUser1 = DebateJoinedUser.createTest(1L, 1L, "string", true);

        DebateJoinedUser disagreeUser2 = DebateJoinedUser.createTest(2L, 1L, "string1", false);


        List<DebateJoinedUser> joinedUsers = List.of(disagreeUser1, disagreeUser2);

        when(userRepository.findByUserName(username)).thenReturn(Optional.of(user));
        when(debateJoinedUserRepository.findIdsByUserName(username)).thenReturn(List.of(debateRoomId));
        when(debateRoomRepository.findAllById(List.of(debateRoomId))).thenReturn(List.of(debateRoom));
        when(movieRepository.findById(any())).thenReturn(Optional.of(movie));
        when(debateJoinedUserRepository.findByDebateRoomId(debateRoomId)).thenReturn(joinedUsers);

        // Act
        UserDto result = userService.getUserDetails(username);

        // Assert
        assertNotNull(result);
        assertEquals(username, result.getName());
        assertEquals(1, result.getJoinedDebateRooms().get(0).getDisagreeJoinedUserNumber()); // 반대 의견 수 확인
    }
}