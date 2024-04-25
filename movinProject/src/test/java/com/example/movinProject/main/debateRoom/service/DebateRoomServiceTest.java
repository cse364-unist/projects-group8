package com.example.movinProject.main.debateRoom.service;

import com.example.movinProject.domain.chat.domain.Chat;
import com.example.movinProject.domain.chat.model.ChatType;
import com.example.movinProject.domain.chat.repository.ChatRepository;
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
import com.example.movinProject.main.chatApiProxy.chatRoom.RealtimeDebateRoom;
import com.example.movinProject.main.debateRoom.dto.*;
import com.example.movinProject.main.movie.dto.MovieDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DebateRoomServiceTest {
    @Mock
    private DebateRoomRepository debateRoomRepository;
    @Mock
    private DebateJoinedUserRepository debateJoinedUserRepository;
    @Mock
    private ChatRepository chatRepository;
    @Mock
    private DebateVoteRepository debateVoteRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private TextMessage textMessage;

    @InjectMocks
    private DebateRoomService debateRoomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    class RealtimeRelatedTest {

        @BeforeEach
        void setUp() throws JsonProcessingException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
            // 데이터베이스 가상 데이터
            // 1. User 생성
            User user1 = User.createTest(1L, "user1", "password1", "email1");
            User user2 = User.createTest(2L, "user2", "password2", "email2");
            when(userRepository.findByUserName("user1")).thenReturn(Optional.of(user1));
            when(userRepository.findByUserName("user2")).thenReturn(Optional.of(user2));

            // 2. DebateRoom 생성
            DebateRoom debateRoom1 = DebateRoom.initTest(1L, "title1", "topic1", StateType.OPEN, LocalDateTime.now(), 1L);
            when(debateRoomRepository.findById(1L)).thenReturn(Optional.of(debateRoom1));

            // 3. DebateJoinedUser 생성 (2명)
            DebateJoinedUser debateJoinedUser1 = DebateJoinedUser.create(1L, "user1", true);
            DebateJoinedUser debateJoinedUser2 = DebateJoinedUser.create(2L, "user2", false);
            when(debateJoinedUserRepository.findByUserNameAndDebateRoomId("user1", 1L)).thenReturn(Optional.of(debateJoinedUser1));
            when(debateJoinedUserRepository.findByUserNameAndDebateRoomId("user2", 1L)).thenReturn(Optional.of(debateJoinedUser2));
            when(debateJoinedUserRepository.findByDebateRoomId(1L)).thenReturn(List.of(debateJoinedUser1, debateJoinedUser2));

            // Set objectMapper to debateRoomService which is private field and objectMapper is Mocked
            ObjectMapper virtualObjectMapper = mock(ObjectMapper.class);
            // if writeValueAsString is called, always return "test"
            when(virtualObjectMapper.writeValueAsString(any())).thenReturn("test");
            ReflectionTestUtils.setField(debateRoomService, "objectMapper", virtualObjectMapper);

            // call init method to debateRoomService which is private method
            Method method = debateRoomService.getClass().getDeclaredMethod("init");
            method.setAccessible(true);
            method.invoke(debateRoomService);
        }

        @Test
        void userEnter() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, JsonProcessingException {
            // 두 명의 유저가 방에 들어감 (Session 2개)
            WebSocketSession virtualSession1 = mock(WebSocketSession.class);
            WebSocketSession virtualSession2 = mock(WebSocketSession.class);

            debateRoomService.userEnter(virtualSession1, 1L, "user1");
            debateRoomService.userEnter(virtualSession2, 1L, "user2");

            // assert
            HashMap<WebSocketSession, Long> sessionMap = (HashMap<WebSocketSession, Long>)ReflectionTestUtils.getField(debateRoomService, "sessionToDebateRoomId");
            Assertions.assertEquals(sessionMap.size(), 2);
        }

        @Test
        void userEnterButNotExistingRoom() {
            // 가상 데이터
            // 두 명의 유저가 방에 들어감 (Session 2개)
            WebSocketSession virtualSession1 = mock(WebSocketSession.class);
            debateRoomService.userEnter(virtualSession1, 1L, "user1");

            // assert throw
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                debateRoomService.userEnter(virtualSession1, 2L, "user1");
            });
        }

        @Test
        void userEnterButNotJoined() {
            // 가상 데이터
            // 두 명의 유저가 방에 들어감 (Session 2개)
            WebSocketSession virtualSession1 = mock(WebSocketSession.class);
            WebSocketSession virtualSession2 = mock(WebSocketSession.class);

            debateRoomService.userEnter(virtualSession1, 1L, "user1");
            debateRoomService.userEnter(virtualSession2, 1L, "user2");

            // Create third user
            User user3 = User.createTest(3L, "user3", "password3", "email3");
            when(userRepository.findByUserName("user3")).thenReturn(Optional.of(user3));

            // call userEnter method with user3
            WebSocketSession virtualSession3 = mock(WebSocketSession.class);

            // assert throw
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                debateRoomService.userEnter(virtualSession3, 1L, "user3");
            });
        }

        @Test
        void userEnterButNotExistingUser() {
            // 가상 데이터
            // 두 명의 유저가 방에 들어감 (Session 2개)
            WebSocketSession virtualSession1 = mock(WebSocketSession.class);
            WebSocketSession virtualSession2 = mock(WebSocketSession.class);

            debateRoomService.userEnter(virtualSession1, 1L, "user1");
            debateRoomService.userEnter(virtualSession2, 1L, "user2");

            // call userEnter method with user3
            WebSocketSession virtualSession3 = mock(WebSocketSession.class);

            // assert throw
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                debateRoomService.userEnter(virtualSession3, 1L, "user3");
            });
        }

        @Test
        void userLeave() {
            // 두 명의 유저가 방에 들어감 (Session 2개)
            WebSocketSession virtualSession1 = mock(WebSocketSession.class);
            WebSocketSession virtualSession2 = mock(WebSocketSession.class);

            debateRoomService.userEnter(virtualSession1, 1L, "user1");
            debateRoomService.userEnter(virtualSession2, 1L, "user2");

            // 유저 퇴장
            debateRoomService.userLeave(virtualSession1);
            debateRoomService.userLeave(virtualSession2);

            // assert
            HashMap<WebSocketSession, Long> sessionMap = (HashMap<WebSocketSession, Long>)ReflectionTestUtils.getField(debateRoomService, "sessionToDebateRoomId");
            Assertions.assertEquals(sessionMap.size(), 0);
        }

        @Test
        void userLeaveTwice() {
            // 가상 데이터
            // 두 명의 유저가 방에 들어감 (Session 2개)
            WebSocketSession virtualSession1 = mock(WebSocketSession.class);
            WebSocketSession virtualSession2 = mock(WebSocketSession.class);

            debateRoomService.userEnter(virtualSession1, 1L, "user1");

            // Delete debateRoom
            when(debateRoomRepository.findById(1L)).thenReturn(Optional.ofNullable(null));

            debateRoomService.userLeave(virtualSession1);

            // assert throw
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                // 유저 퇴장
                debateRoomService.userLeave(virtualSession1);
            });
        }

        @Test
        void handleErrorWhenEnterAndSendMessage() throws IOException {
            // 두 명의 유저가 방에 들어감 (Session 2개)
            WebSocketSession virtualSession1 = mock(WebSocketSession.class);
            WebSocketSession virtualSession2 = mock(WebSocketSession.class);

            // throw error when sendMessage
            Mockito.doThrow(new IOException()).when(virtualSession1).sendMessage(any(WebSocketMessage.class));
            Mockito.doThrow(new IOException()).when(virtualSession2).sendMessage(any(WebSocketMessage.class));

            // assert not throw
            Assertions.assertDoesNotThrow(() -> {
                debateRoomService.userEnter(virtualSession1, 1L, "user1");
                debateRoomService.userEnter(virtualSession2, 1L, "user2");
            });
        }

        @Test
        void handleErrorWhenLeaveAndSendMessage() throws IOException {
            // 두 명의 유저가 방에 들어감 (Session 2개)
            WebSocketSession virtualSession1 = mock(WebSocketSession.class);
            WebSocketSession virtualSession2 = mock(WebSocketSession.class);

            debateRoomService.userEnter(virtualSession1, 1L, "user1");
            debateRoomService.userEnter(virtualSession2, 1L, "user2");

            // throw error when sendMessage
            Mockito.doThrow(new IOException()).when(virtualSession1).sendMessage(any(WebSocketMessage.class));
            Mockito.doThrow(new IOException()).when(virtualSession2).sendMessage(any(WebSocketMessage.class));

            // assert not throw
            Assertions.assertDoesNotThrow(() -> {
                debateRoomService.userLeave(virtualSession1);
                debateRoomService.userLeave(virtualSession2);
            });
        }

        @Test
        void endDebateRoomByStepFinish() {
            // mock ChatGptService
            ChatGPTService chatGptService = mock(ChatGPTService.class);
            ReflectionTestUtils.setField(debateRoomService, "chatGPTService", chatGptService);
            when(chatGptService.summarizeOpinions(any())).thenReturn(
                    List.of(new String[]{"summary1", "summary2"})
            );

            // 두 명의 유저가 방에 들어감 (Session 2개)
            WebSocketSession virtualSession1 = mock(WebSocketSession.class);
            WebSocketSession virtualSession2 = mock(WebSocketSession.class);

            debateRoomService.userEnter(virtualSession1, 1L, "user1");

            RealtimeDebateRoom realtimeDebateRoom = debateRoomService.findRoomById(1L);
            ReflectionTestUtils.setField(realtimeDebateRoom, "factor", 600000);

            debateRoomService.userEnter(virtualSession2, 1L, "user2");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // assert
            Assertions.assertEquals(7, ReflectionTestUtils.getField(realtimeDebateRoom, "currentDebateStep"));
        }

        @Test
        void errorWhenSummarize() {
            // assert throw
            ChatGPTService chatGptService = mock(ChatGPTService.class);
            ReflectionTestUtils.setField(debateRoomService, "chatGPTService", chatGptService);
            when(chatGptService.summarizeOpinions(any())).thenReturn(
                    List.of(new String[]{"summary1", "summary2"})
            );

            // 두 명의 유저가 방에 들어감 (Session 2개)
            WebSocketSession virtualSession1 = mock(WebSocketSession.class);
            WebSocketSession virtualSession2 = mock(WebSocketSession.class);

            debateRoomService.userEnter(virtualSession1, 1L, "user1");

            RealtimeDebateRoom realtimeDebateRoom = debateRoomService.findRoomById(1L);
            ReflectionTestUtils.setField(realtimeDebateRoom, "factor", 600000);



            debateRoomService.userEnter(virtualSession2, 1L, "user2");
            // DELETE DebateRoom from repository
            when(debateRoomRepository.findById(1L)).thenReturn(Optional.ofNullable(null));

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            verify(debateRoomRepository, times(2)).save(any());
        }

        @Test
        void userChat() throws IOException {
            // 두 명의 유저가 방에 들어감 (Session 2개)
            WebSocketSession virtualSession1 = mock(WebSocketSession.class);
            WebSocketSession virtualSession2 = mock(WebSocketSession.class);

            debateRoomService.userEnter(virtualSession1, 1L, "user1");
            debateRoomService.userEnter(virtualSession2, 1L, "user2");

            debateRoomService.chatMessageReceived(virtualSession1, "message1");

            verify(virtualSession2, times(4)).sendMessage(any(WebSocketMessage.class));
        }

        @Test
        void userChatButInvalidSession() {
            // 두 명의 유저가 방에 들어감 (Session 2개)
            WebSocketSession virtualSession1 = mock(WebSocketSession.class);
            WebSocketSession virtualSession2 = mock(WebSocketSession.class);

            debateRoomService.userEnter(virtualSession1, 1L, "user1");
            debateRoomService.userEnter(virtualSession2, 1L, "user2");

            // assert throw
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                debateRoomService.chatMessageReceived(mock(WebSocketSession.class), "message1");
            });
        }

        @Test
        void handleErrorWhenChatAndSendMessage() throws IOException {
            // 두 명의 유저가 방에 들어감 (Session 2개)
            WebSocketSession virtualSession1 = mock(WebSocketSession.class);
            WebSocketSession virtualSession2 = mock(WebSocketSession.class);

            debateRoomService.userEnter(virtualSession1, 1L, "user1");
            debateRoomService.userEnter(virtualSession2, 1L, "user2");

            // throw error when sendMessage
            Mockito.doThrow(new IOException()).when(virtualSession1).sendMessage(any(WebSocketMessage.class));
            Mockito.doThrow(new IOException()).when(virtualSession2).sendMessage(any(WebSocketMessage.class));

            // assert not throw
            Assertions.assertDoesNotThrow(() -> {
                debateRoomService.chatMessageReceived(virtualSession1, "message1");
            });
        }
    }




    @Nested
    class RestApiTest {

        private Movie movie1;
        private DebateRoom debateRoom1;
        private DebateRoom debateRoom2;
        private DebateRoom debateRoom3;
        private DebateRoom debateRoom4;
        private DebateRoom debateRoom5;
        private DebateRoom debateRoom6;

        private DebateVote vote1;
        private DebateVote vote2;

        private User user1;
        private User user2;
        private User user3;

        private DebateJoinedUser debateJoinedUser1;
        private DebateJoinedUser debateJoinedUser2;

        private Chat chat1;
        private Chat chat2;

        @BeforeEach
        void setUp() {
            LocalDateTime now = LocalDateTime.now();
            movie1 = Movie.createTest(1L, "title1", "comic", 3.7, "url1", "description1");

            debateRoom1 = DebateRoom.initTest(1L, "title1", "topic1", StateType.OPEN, now, 1L);
            debateRoom2 = DebateRoom.initTest(2L, "title1", "topic2", StateType.CLOSE, now, 1L);
            debateRoom3 = DebateRoom.initTest(3L, "title1", "topic3", StateType.OPEN, now, 1L);
            debateRoom4 = DebateRoom.initTest(4L, "title1", "topic4", StateType.VOTE, now, 1L);
            debateRoom5 = DebateRoom.initTest(5L, "title1", "topic5", StateType.OPEN, now, 1L);
            debateRoom6 = DebateRoom.initTest(6L, "title1", "topic6", StateType.VOTE, now, 1L);

            vote1 = DebateVote.createTest(1L, 1L, "user1", true, now);
            vote2 = DebateVote.createTest(2L, 1L, "user2", true, now);

            user1 = User.createTest(1L, "user1", "password1", "email1");
            user2 = User.createTest(2L, "user2", "password2", "email2");

            user3 = User.createTest(3L, "user3", "password3", "email3");


            debateJoinedUser1 = DebateJoinedUser.create(1L, "user1", true);
            debateJoinedUser2 = DebateJoinedUser.create(2L, "user2", false);

            chat1 = Chat.createTest(1L, "message1", ChatType.AGREE, now, 1L);
            chat2 = Chat.createTest(2L, "message2", ChatType.DISAGREE, now, 1L);
        }

        @Test
        void getDebateRoomsGroupedByStateByMovieId() {
            // debateRoomRepository.findByMovieId(movieId);
            // movieRepository.findById(movieId).orElse(null);
            Long movieId = 1L;
            String openDebateRoomString = "openDebateRooms";
            String voteDebateRoomString = "voteDebateRooms";
            when(debateRoomRepository.findByMovieId(movieId)).thenReturn(List.of(debateRoom1, debateRoom2, debateRoom3
                    , debateRoom4, debateRoom5, debateRoom6));
            when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie1));
            Map<String, List<DebateRoomDto>> result = debateRoomService.getDebateRoomsGroupedByStateByMovieId(movieId);
//            System.out.println(result.get(openDebateRoomString).get(0).getTopic());
//            System.out.println(result.get(openDebateRoomString).get(1).getTopic());
//            System.out.println(result.get(openDebateRoomString).get(2).getTopic());
//
//            System.out.println(result.get(voteDevateRoomString).get(0).getTopic());
//            System.out.println(result.get(voteDevateRoomString).get(1).getTopic());


            // assertions
            List<DebateRoomDto> openDebateRoomDtos = Stream.of(debateRoom1, debateRoom3, debateRoom5)
                    .map(room -> {
                        return DebateRoomDto.builder()
                                .id(room.getId())
                                .title(room.getTitle())
                                .topic(room.getTopic())
                                .movie(MovieDto.builder()
                                        .name(movie1.getTitle())
                                        .id(movie1.getId())
                                        .thumbnailUrl(movie1.getThumbnailUrl())
                                        .build())
                                .startTime(room.getStartTime())
                                .duration(room.getDuration())
                                .maxUserNumber(room.getMaxUserNumber())
                                .build();
                    })
                    .toList();
            List<DebateRoomDto> voteDebateRoomDtos = Stream.of(debateRoom4, debateRoom6)
                    .map(room -> {
                        return DebateRoomDto.builder()
                                .id(room.getId())
                                .title(room.getTitle())
                                .topic(room.getTopic())
                                .movie(MovieDto.builder()
                                        .name(movie1.getTitle())
                                        .id(movie1.getId())
                                        .thumbnailUrl(movie1.getThumbnailUrl())
                                        .build())
                                .startTime(room.getStartTime())
                                .duration(room.getDuration())
                                .maxUserNumber(room.getMaxUserNumber())
                                .build();
                    })
                    .toList();
            for (int i = 0; i < openDebateRoomDtos.size(); i++) {
                assertAllFieldsInDebateRoomDto(result.get(openDebateRoomString).get(i), openDebateRoomDtos.get(i));
            }
            for (int i = 0; i < voteDebateRoomDtos.size(); i++) {
                assertAllFieldsInDebateRoomDto(result.get(voteDebateRoomString).get(i), voteDebateRoomDtos.get(i));
            }

        }

        void assertAllFieldsInDebateRoomDto(DebateRoomDto resultDto, DebateRoomDto expectedDto) {
            // movieDto assertion
            Assertions.assertEquals(resultDto.getMovie().getId(), expectedDto.getMovie().getId());
            Assertions.assertEquals(resultDto.getMovie().getThumbnailUrl(), expectedDto.getMovie().getThumbnailUrl());
            Assertions.assertEquals(resultDto.getMovie().getName(), expectedDto.getMovie().getName());

            //
            Assertions.assertEquals(resultDto.getTitle(), expectedDto.getTitle());
            Assertions.assertEquals(resultDto.getId(), expectedDto.getId());
            Assertions.assertEquals(resultDto.getState(), expectedDto.getState());
            Assertions.assertEquals(resultDto.getDuration(), expectedDto.getDuration());
            Assertions.assertEquals(resultDto.getTopic(), expectedDto.getTopic());
            Assertions.assertEquals(resultDto.getAgreeJoinedUserNumber(), expectedDto.getAgreeJoinedUserNumber());
            Assertions.assertEquals(resultDto.getDisagreeJoinedUserNumber(), expectedDto.getDisagreeJoinedUserNumber());
            Assertions.assertEquals(resultDto.getMaxUserNumber(), expectedDto.getMaxUserNumber());
            Assertions.assertEquals(resultDto.getStartTime(), expectedDto.getStartTime());


        }

        @Test
        void getDebateRoomDetails() {
            // debateRoomRepository.findById(id).orElse(null);
            // debateVoteRepository.findByUserNameAndDebateRoomId(userName, id);
            // debateJoinedUserRepository.findByUserNameAndDebateRoomId(userName, id);
            // chatRepository.findByDebateRoomId(id);
            Long movieId = 1L;
            Long debateRoomId = 1L;
            String userName = "user1";
            when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie1));
            when(debateRoomRepository.findById(debateRoomId)).thenReturn(Optional.of(this.debateRoom1));
            when(debateVoteRepository.findByUserNameAndDebateRoomId(userName, debateRoomId)).thenReturn(vote1);
            when(debateJoinedUserRepository.findByUserNameAndDebateRoomId(userName, debateRoomId)).thenReturn(Optional.of(debateJoinedUser1));
            when(debateJoinedUserRepository.findByDebateRoomId(debateRoomId)).thenReturn(List.of(debateJoinedUser1));
            when(chatRepository.findByDebateRoomId(debateRoomId)).thenReturn(List.of(chat1, chat2));
            DebateRoomChatDto resultDto = debateRoomService.getDebateRoomDetails(debateRoomId, userName);
            // make expectation
            DebateRoomChatDto expectedDto = new DebateRoomChatDto();
            expectedDto.setTitle(debateRoom1.getTitle());
            expectedDto.setTopic(debateRoom1.getTopic());
            expectedDto.setStateType(debateRoom1.getStateType());
            expectedDto.setStartTime(debateRoom1.getStartTime());
            expectedDto.setDuration(debateRoom1.getDuration());
            expectedDto.setMaxUserNumber(debateRoom1.getMaxUserNumber());
            expectedDto.setMovie(MovieDto.builder()
                    .name(movie1.getTitle())
                    .id(movie1.getId())
                    .thumbnailUrl(movie1.getThumbnailUrl())
                    .build());
            expectedDto.setAgreeJoinedUserNumber(1);
            expectedDto.setDisagreeJoinedUserNumber(0);
            expectedDto.setChats(List.of(chat1, chat2));
            // user1은 debateRoom1에 참여 + agree를 했음.
            expectedDto.setVoted(true);
            expectedDto.setVoteAgree(vote1.isAgree());

            expectedDto.setJoined(true);
            expectedDto.setAgree(debateJoinedUser1.isAgree());

            // assertion
            assertDebateRoomChatDto(resultDto, expectedDto);

        }

        void assertDebateRoomChatDto(DebateRoomChatDto resultDto, DebateRoomChatDto expectedDto) {
            Assertions.assertEquals(resultDto.getAgreeJoinedUserNumber(), expectedDto.getAgreeJoinedUserNumber());
            Assertions.assertEquals(resultDto.getDisagreeJoinedUserNumber(), expectedDto.getDisagreeJoinedUserNumber());
            Assertions.assertEquals(resultDto.getMaxUserNumber(), expectedDto.getMaxUserNumber());
            Assertions.assertEquals(resultDto.getChats(), expectedDto.getChats());
            Assertions.assertEquals(resultDto.getTitle(), expectedDto.getTitle());
            Assertions.assertEquals(resultDto.getDuration(), expectedDto.getDuration());
            Assertions.assertEquals(resultDto.getStateType(), expectedDto.getStateType());
            Assertions.assertEquals(resultDto.getTopic(), expectedDto.getTopic());
            Assertions.assertEquals(resultDto.getStartTime(), expectedDto.getStartTime());
            // movieDto assertions
            Assertions.assertEquals(resultDto.getMovie().getName(), expectedDto.getMovie().getName());
            Assertions.assertEquals(resultDto.getMovie().getId(), expectedDto.getMovie().getId());
            Assertions.assertEquals(resultDto.getMovie().getThumbnailUrl(), expectedDto.getMovie().getThumbnailUrl());
            Assertions.assertEquals(resultDto.isAgree(), expectedDto.isAgree());
            Assertions.assertEquals(resultDto.isVoteAgree(), expectedDto.isVoteAgree());
            Assertions.assertEquals(resultDto.isVoted(), expectedDto.isVoted());
            Assertions.assertEquals(resultDto.isJoined(), expectedDto.isJoined());

        }

        @Test
        void castjoin() {
            // debateJoinedUserRepository.findByUserNameAndDebateRoomId(username, id);
            // debateRoomRepository.findById(id).orElse(null);
            // userRepository.findByUserName(username).orElse(null);
            // debateVoteRepository.findByUserNameAndDebateRoomId(username, id);
            String userName = "user3";
            Long debateRoomId = 1L;
            Long movieId = 1L;
            boolean agree = true;
            when(debateJoinedUserRepository.findByUserNameAndDebateRoomId(userName, debateRoomId)).thenReturn(Optional.ofNullable(null));
            when(debateRoomRepository.findById(debateRoomId)).thenReturn(Optional.of(debateRoom1));
            when(userRepository.findByUserName(userName)).thenReturn(Optional.of(user1));
            when(debateVoteRepository.findByUserNameAndDebateRoomId(userName, debateRoomId)).thenReturn(vote1);
            when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie1));

            // then
            DebateRoomVoteDto resultDto = debateRoomService.castjoin(debateRoomId, userName, agree);

            // expectation
            DebateRoomVoteDto expectedDto = new DebateRoomVoteDto();
            expectedDto.setTitle(debateRoom1.getTitle());
            expectedDto.setTopic(debateRoom1.getTopic());
            expectedDto.setStateType(debateRoom1.getStateType());
            expectedDto.setStartTime(debateRoom1.getStartTime());
            expectedDto.setDuration(debateRoom1.getDuration());
            expectedDto.setMaxUserNumber(debateRoom1.getMaxUserNumber());
            expectedDto.setMovie(MovieDto.builder()
                    .thumbnailUrl(movie1.getThumbnailUrl())
                    .name(movie1.getTitle())
                    .id(movie1.getId())
                    .build());
            // 고정
            expectedDto.setAgree(agree);
            expectedDto.setJoined(true);

            // 경우의 수마다 변동.
//            expectedDto.setVoted(true);
//            expectedDto.setVoteAgree();
            assertDebateRoomVoteDto(resultDto, expectedDto);
        }

        void assertDebateRoomVoteDto(DebateRoomVoteDto resultDto, DebateRoomVoteDto expectedDto) {
            Assertions.assertEquals(resultDto.getTitle(), expectedDto.getTitle());
            Assertions.assertEquals(resultDto.getTopic(), expectedDto.getTopic());
            Assertions.assertEquals(resultDto.getDuration(), expectedDto.getDuration());
            Assertions.assertEquals(resultDto.getStartTime(), expectedDto.getStartTime());
            Assertions.assertEquals(resultDto.getStateType(), expectedDto.getStateType());
            Assertions.assertEquals(resultDto.getMaxUserNumber(), expectedDto.getMaxUserNumber());
            // movie
            Assertions.assertEquals(resultDto.getMovie().getId(), expectedDto.getMovie().getId());
            Assertions.assertEquals(resultDto.getMovie().getName(), expectedDto.getMovie().getName());
            Assertions.assertEquals(resultDto.getMovie().getThumbnailUrl(), expectedDto.getMovie().getThumbnailUrl());

        }

        @Test
        void castVote() {
            // debateRoomRepository.findById(id).orElse(null);
            // userRepository.findByUserName(username).orElse(null);
            // debateJoinedUserRepository.findByUserNameAndDebateRoomId(username, id);
            Long movieId = 1L;
            Long debateRoomId = 1L;
            String userName = "user1";
            boolean agree = true;

            when(debateRoomRepository.findById(debateRoomId)).thenReturn(Optional.ofNullable(debateRoom1));
            when(userRepository.findByUserName(userName)).thenReturn(Optional.of(user1));
            when(debateJoinedUserRepository.findByUserNameAndDebateRoomId(userName, debateRoomId)).thenReturn(Optional.of(debateJoinedUser1));
            when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie1));
            DebateRoomVoteDto resultDto = debateRoomService.castVote(debateRoomId, userName, agree);
            // expectation
            DebateRoomVoteDto dto = new DebateRoomVoteDto();
            dto.setTitle(debateRoom1.getTitle());
            dto.setTopic(debateRoom1.getTopic());
            dto.setStateType(debateRoom1.getStateType());
            dto.setStartTime(debateRoom1.getStartTime());
            dto.setDuration(debateRoom1.getDuration());
            dto.setMaxUserNumber(debateRoom1.getMaxUserNumber());
            dto.setMovie(MovieDto.builder()
                    .name(movie1.getTitle())
                    .thumbnailUrl(movie1.getThumbnailUrl())
                    .id(movie1.getId())
                    .build());

            // 고정
            dto.setVoted(true);
            dto.setVoteAgree(agree);
            // 변동
            dto.setJoined(true);
            dto.setAgree(true);
            assertDebateRoomVoteDto(resultDto, dto);

        }

        @Test
        void resultVoteInfo() {
            // debateRoomRepository.findById(id).orElseThrow(() -> new BadRequestException(BadRequestType.CANNOT_FIND_DEBATE_ROOM));
            // debateVoteRepository.findByDebateRoomId(id);
            // userRepository.findByUserName
            Long debateRoomId = 1L;
            List<DebateVote> votes = List.of(vote1, vote2);
            List<DebateJoinedUser> allDebateJoinedUser = List.of(debateJoinedUser1);


            when(debateRoomRepository.findById(debateRoomId)).thenReturn(Optional.ofNullable(debateRoom1));
            when(debateVoteRepository.findByDebateRoomId(debateRoomId)).thenReturn(votes);
            when(userRepository.findByUserName(user1.getUserName())).thenReturn(Optional.of(user1));
            when(userRepository.findByUserName(user2.getUserName())).thenReturn(Optional.of(user2));

            VoteDto resultDto = debateRoomService.resultVoteInfo(debateRoomId);

            // expectation
            VoteDto expectedDto = VoteDto.builder()
                    .title(debateRoom1.getTitle())
                    .topic(debateRoom1.getTopic())
                    .state(debateRoom1.getStateType())
                    .startTime(debateRoom1.getStartTime())
                    .duration(debateRoom1.getDuration())
                    .build();

            // assertion
            assertVoteDto(resultDto, expectedDto);
        }

        void assertVoteDto(VoteDto resultDto, VoteDto expectedDto) {
            Assertions.assertEquals(resultDto.getTitle(), expectedDto.getTitle());
            Assertions.assertEquals(resultDto.getTopic(), expectedDto.getTopic());
            Assertions.assertEquals(resultDto.getState(), expectedDto.getState());
            Assertions.assertEquals(resultDto.getDuration(), expectedDto.getDuration());
            Assertions.assertEquals(resultDto.getStartTime(), expectedDto.getStartTime());


        }

        @Test
        void create() {
            // debateRoomRepository.save(debateRoom)
//            LocalDateTime now = LocalDateTime.now();
//            Long movieId = 1L;
//
//            DebateRoomCreateDto dto = new DebateRoomCreateDto();
//            dto.setTitle("title1");
//            dto.setTopic("topic1");
//            dto.setStartTime(now);
//            dto.setMovieId(movieId);
//            DebateRoom debateRoom = DebateRoom.initTest(1L, dto.getTitle(), dto.getTopic(), StateType.OPEN, dto.getStartTime(), dto.getMovieId());
//            when(debateRoomRepository.save(debateRoom)).thenReturn(debateRoom);
//
//            Long result = debateRoomService.create(dto);
//
//
//
//
//            Assertions.assertEquals(result, debateRoom.getId());
        }
    }


}