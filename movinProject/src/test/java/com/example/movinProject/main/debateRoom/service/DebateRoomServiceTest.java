package com.example.movinProject.main.debateRoom.service;

import com.example.movinProject.config.exception.BadRequestException;
import com.example.movinProject.config.exception.BadRequestType;
import com.example.movinProject.domain.chat.domain.Chat;
import com.example.movinProject.domain.chat.repository.ChatRepository;
import com.example.movinProject.domain.debateJoinedUser.repository.DebateJoinedUserRepository;
import com.example.movinProject.domain.debateJoinedUser.domain.DebateJoinedUser;
import com.example.movinProject.domain.debateRoom.domain.DebateRoom;
import com.example.movinProject.domain.debateRoom.model.StateType;
import com.example.movinProject.domain.debateRoom.repository.DebateRoomRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import com.example.movinProject.domain.debateVote.domain.DebateVote;
import com.example.movinProject.domain.debateVote.repository.DebateVoteRepository;
import com.example.movinProject.domain.movie.domain.Movie;
import com.example.movinProject.domain.movie.repository.MovieRepository;
import com.example.movinProject.domain.user.domain.User;
import com.example.movinProject.domain.user.repository.UserRepository;
import com.example.movinProject.main.debateRoom.dto.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.movinProject.main.chatApiProxy.chatRoom.RealtimeDebateRoom;
import com.example.movinProject.main.chatApiProxy.dto.RealtimeMessageDto;
import com.example.movinProject.main.movie.dto.MovieDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;

import com.example.movinProject.domain.debateRoom.model.StateType;
import java.time.LocalDateTime;
import com.example.movinProject.main.debateRoom.dto.*;
import static org.junit.jupiter.api.Assertions.*;

class DebateRoomServiceTest {

    @Mock
    private DebateRoomRepository debateRoomRepository;

    @Mock
    private DebateVoteRepository debateVoteRepository;

    @Mock
    private DebateJoinedUserRepository debateJoinedUserRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DebateRoomService debateRoomService;

    private DebateRoom debateRoom1;

    private DebateVote debateVote1;
    private DebateVote debateVote2;
    private DebateVote debateVote3;

    private DebateJoinedUser duser1;
    private DebateJoinedUser duser2;

    private User user1;
    private User user2;
    private User user3;
    private User juser1;
    private User juser2;
 
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        debateRoom1 = DebateRoom.initTest(1L, "title", "topic", StateType.OPEN, LocalDateTime.now(), 1L);

        debateVote1 = DebateVote.createTest(1L, 1L, "name1", true, LocalDateTime.now());
        debateVote2 = DebateVote.createTest(2L, 1L, "name2", false, LocalDateTime.now());
        debateVote3 = DebateVote.createTest(3L, 1L, "name3", true, LocalDateTime.now());

        duser1 = DebateJoinedUser.createTest(1L, 1L, "juser1", true);
        duser2 = DebateJoinedUser.createTest(2L, 1L, "juser2", false);
    
        user1 = User.createTest(1L, "name1", "1111", "1@ex.com");
        user2 = User.createTest(2L, "name2", "1111", "1@ex.com");
        user3 = User.createTest(3L, "name3", "1111", "1@ex.com");
        juser1 = User.createTest(3L, "juser1", "1111", "1@ex.com");
        juser2 = User.createTest(3L, "juser2", "1111", "1@ex.com");
    }

    @Test
    void findRoomById() {
    }

    @Test
    void create() {
    }

    @Test
    void startDebate() {
    }

    @Test
    void userEnter() {
    }

    @Test
    void userLeave() {
    }

    @Test
    void chatMessageReceived() {
    }

    @Test
    void getDebateRoomsGroupedByStateByMovieId() {
    }

    @Test
    void getDebateRoomDetails() {
    }

    @Test
    void castjoin() {
    }

    @Test
    void castVote() {
    }

    @Test
    void resultVoteInfo() {
        Long testId = 1L;
        List<DebateVote> list = List.of(debateVote1, debateVote2, debateVote3);
        List<DebateJoinedUser> list2 = List.of(duser1, duser2);
        when(debateRoomRepository.findById(testId)).thenReturn(Optional.of(debateRoom1));
        when(debateVoteRepository.findByDebateRoomId(testId)).thenReturn(list);
        when(userRepository.findByUserName("name1")).thenReturn(Optional.of(user1));
        when(userRepository.findByUserName("name2")).thenReturn(Optional.of(user2));
        when(userRepository.findByUserName("name3")).thenReturn(Optional.of(user3));
        when(userRepository.findByUserName("juser1")).thenReturn(Optional.of(juser1));
        when(userRepository.findByUserName("juser2")).thenReturn(Optional.of(juser2));
        when(debateJoinedUserRepository.findByDebateRoomId(testId)).thenReturn(list2);
        VoteDto voteInfo = debateRoomService.resultVoteInfo(testId);

        //testing
        Assertions.assertEquals(debateRoom1.getTitle(),voteInfo.getTitle());
        Assertions.assertEquals(debateRoom1.getTopic(),voteInfo.getTopic());
        Assertions.assertEquals(StateType.CLOSE,voteInfo.getState());
        Assertions.assertEquals(debateRoom1.getStartTime(),voteInfo.getStartTime());
        Assertions.assertEquals(debateRoom1.getDuration(),voteInfo.getDuration());
        Assertions.assertEquals(debateRoom1.getMaxUserNumber(),voteInfo.getMaxUserNumber());
    }
}