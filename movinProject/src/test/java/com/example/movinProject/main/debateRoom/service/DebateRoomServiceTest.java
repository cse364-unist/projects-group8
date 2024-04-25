package com.example.movinProject.main.debateRoom.service;

import com.example.movinProject.config.exception.BadRequestType;
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
import com.example.movinProject.main.debateRoom.dto.*;
import com.example.movinProject.main.movie.dto.MovieDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

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

    @InjectMocks
    private DebateRoomService debateRoomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findRoomById() {
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

    @Nested
    class RestApiTest {

        private Movie movie1;
        private DebateRoom debateRoom1;
        private DebateRoom debateRoom2;
        private DebateRoom debateRoom3;
        private DebateRoom debateRoom4;
        private DebateRoom debateRoom5;
        private DebateRoom debateRoom6;
        private DebateRoom openDebateRoom;
        private DebateRoom voteDebateRoom;

        private DebateVote vote1;
        private DebateVote vote2;
        private DebateVote vote3;
        private DebateVote vote4;

        private User user1;
        private User user2;
        private User user3;
        private User user4;

        private DebateJoinedUser debateJoinedUser1;
        private DebateJoinedUser debateJoinedUser2;
        private DebateJoinedUser debateJoinedUser3;

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

            openDebateRoom = DebateRoom.initTest(7L, "title1", "topic7", StateType.OPEN, now, 5L);
            voteDebateRoom = DebateRoom.initTest(8L, "title1", "topic8", StateType.VOTE, now, 7L);


            vote1 = DebateVote.createTest(1L, 1L, "user1", true, now);
            vote2 = DebateVote.createTest(2L, 1L, "user2", true, now);
            vote3 = DebateVote.createTest(3L, 1L, "user3", false, now);
            vote4 = DebateVote.createTest(4L, 1L, "user4", false, now);

            user1 = User.createTest(1L, "user1", "password1", "email1");
            user2 = User.createTest(2L, "user2", "password2", "email2");

            user3 = User.createTest(3L, "user3", "password3", "email3");
            user4 = User.createTest(4L, "user4", "password4", "email4");

            debateJoinedUser1 = DebateJoinedUser.create(1L, "user1", true);
            debateJoinedUser2 = DebateJoinedUser.create(2L, "user2", false);
            debateJoinedUser3 = DebateJoinedUser.create(1L, "user3", true);

            chat1 = Chat.createTest(1L, "message1", ChatType.AGREE, now, 1L);
            chat2 = Chat.createTest(2L, "message2", ChatType.DISAGREE, now, 1L);

        }

        @Test
        void getDebateRoomsGroupedByStateByMovieIdWithNoException() {
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

        @Test
        void getDebateRoomsGroupedByStateByMovieIdWithExceptionOpen() {
            Long movieId = 5L;
            when(debateRoomRepository.findByMovieId(movieId)).thenReturn(List.of(openDebateRoom));
            when(movieRepository.findById(movieId)).thenReturn(Optional.ofNullable(null));
            Assertions.assertThrows(RuntimeException.class, () -> debateRoomService.getDebateRoomsGroupedByStateByMovieId(movieId));
        }

        @Test
        void getDebateRoomsGroupedByStateByMovieIdWithExceptionVote() {
            Long movieId = 7L;
            when(debateRoomRepository.findByMovieId(movieId)).thenReturn(List.of(voteDebateRoom));
            when(movieRepository.findById(movieId)).thenReturn(Optional.ofNullable(null));
            Assertions.assertThrows(RuntimeException.class, () -> debateRoomService.getDebateRoomsGroupedByStateByMovieId(movieId));

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
        void getDebateRoomDetails1() {
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
//            Assertions.assertThrows(BadRequestType.CANNOT_FIND_DEBATE_ROOM, debateRoomService.getDebateRoomDetails(debateRoomId, userName));
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

        @Test
        void castjoinExistJoinedUserException() {
            String userName = "user3";
            Long debateRoomId = 1L;
            Long movieId = 1L;
            boolean agree = true;
            when(debateJoinedUserRepository.findByUserNameAndDebateRoomId(userName, debateRoomId)).thenReturn(Optional.ofNullable(debateJoinedUser1));
            Assertions.assertThrows(RuntimeException.class, () -> debateRoomService.castjoin(debateRoomId, userName, agree));

        }

        @Test
        void castjoinDebateRoomException() {
            String userName = "user3";
            Long debateRoomId = 1L;
            Long movieId = 1L;
            boolean agree = true;
            when(debateJoinedUserRepository.findByUserNameAndDebateRoomId(userName, debateRoomId)).thenReturn(Optional.ofNullable(null));
            when(debateRoomRepository.findById(debateRoomId)).thenReturn(Optional.ofNullable(null));

            Assertions.assertThrows(RuntimeException.class, () -> debateRoomService.castjoin(debateRoomId, userName, agree));

        }

        @Test
        void castjoinUserException() {
            String userName = "user3";
            Long debateRoomId = 1L;
            Long movieId = 1L;
            boolean agree = true;
            when(debateJoinedUserRepository.findByUserNameAndDebateRoomId(userName, debateRoomId)).thenReturn(Optional.ofNullable(null));
            when(debateRoomRepository.findById(debateRoomId)).thenReturn(Optional.of(debateRoom1));
            when(userRepository.findByUserName(userName)).thenReturn(Optional.ofNullable(null));

            Assertions.assertThrows(RuntimeException.class, () -> debateRoomService.castjoin(debateRoomId, userName, agree));
        }

        @Test
        void castjoinMovieException() {
            String userName = "user3";
            Long debateRoomId = 1L;
            Long movieId = 1L;
            boolean agree = true;
            when(debateJoinedUserRepository.findByUserNameAndDebateRoomId(userName, debateRoomId)).thenReturn(Optional.ofNullable(null));
            when(debateRoomRepository.findById(debateRoomId)).thenReturn(Optional.of(debateRoom1));
            when(userRepository.findByUserName(userName)).thenReturn(Optional.of(user1));
            when(movieRepository.findById(movieId)).thenReturn(Optional.ofNullable(null));

            Assertions.assertThrows(RuntimeException.class, () -> debateRoomService.castjoin(debateRoomId, userName, agree));

        }

        @Test
        void castjoinDebateVoteNull() {
            String userName = "user3";
            Long debateRoomId = 1L;
            Long movieId = 1L;
            boolean agree = true;
            when(debateJoinedUserRepository.findByUserNameAndDebateRoomId(userName, debateRoomId)).thenReturn(Optional.ofNullable(null));
            when(debateRoomRepository.findById(debateRoomId)).thenReturn(Optional.of(debateRoom1));
            when(userRepository.findByUserName(userName)).thenReturn(Optional.of(user1));
            when(debateVoteRepository.findByUserNameAndDebateRoomId(userName, debateRoomId)).thenReturn(null);
            when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie1));

            DebateRoomVoteDto resultDto = debateRoomService.castjoin(debateRoomId, userName, agree);
            Assertions.assertFalse(resultDto.isVoted());
            Assertions.assertFalse(resultDto.isVoteAgree());
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

            when(debateJoinedUserRepository.findByDebateRoomId(debateRoomId)).thenReturn(allDebateJoinedUser);
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

        @Test
        void resultVoteInfoDebateRoomException() {
            Long debateRoomId = 1L;
            List<DebateVote> votes = List.of(vote1, vote2);
            List<DebateJoinedUser> allDebateJoinedUser = List.of(debateJoinedUser1);

            when(debateRoomRepository.findById(debateRoomId)).thenReturn(Optional.ofNullable(null));
            Assertions.assertThrows(RuntimeException.class, () -> debateRoomService.resultVoteInfo(debateRoomId));
        }

        @Test
        void resultVoteInfoDisagreeVote() {
            Long debateRoomId = 1L;
            List<DebateVote> votes = List.of(vote1, vote3, vote4);
            List<DebateJoinedUser> allDebateJoinedUser = List.of(debateJoinedUser1);


            when(debateRoomRepository.findById(debateRoomId)).thenReturn(Optional.ofNullable(debateRoom1));
            when(debateVoteRepository.findByDebateRoomId(debateRoomId)).thenReturn(votes);
            when(debateJoinedUserRepository.findByDebateRoomId(debateRoomId)).thenReturn(allDebateJoinedUser);
            when(userRepository.findByUserName(user1.getUserName())).thenReturn(Optional.of(user1));
            when(userRepository.findByUserName(user2.getUserName())).thenReturn(Optional.of(user2));
            when(userRepository.findByUserName(user3.getUserName())).thenReturn(Optional.of(user3));
            when(userRepository.findByUserName(user4.getUserName())).thenReturn(Optional.of(user4));


            VoteDto resultDto = debateRoomService.resultVoteInfo(debateRoomId);
            Assertions.assertEquals(resultDto.getState(), debateRoom1.getStateType());
        }

        @Test
        void resultVoteInfoUserExceptionInAgree() {
            Long debateRoomId = 1L;
            List<DebateVote> votes = List.of(vote1, vote2, vote3);
            List<DebateJoinedUser> allDebateJoinedUser = List.of(debateJoinedUser1);


            when(debateRoomRepository.findById(debateRoomId)).thenReturn(Optional.ofNullable(debateRoom1));
            when(debateVoteRepository.findByDebateRoomId(debateRoomId)).thenReturn(votes);
            when(userRepository.findByUserName(user1.getUserName())).thenReturn(Optional.of(user1));
            when(userRepository.findByUserName(user2.getUserName())).thenReturn(Optional.ofNullable(null));
            when(userRepository.findByUserName(user3.getUserName())).thenReturn(Optional.of(user3));

            Assertions.assertThrows(RuntimeException.class, () -> debateRoomService.resultVoteInfo(debateRoomId));
        }

        @Test
        void resultVoteInfoUserExceptionInDisagree() {
            Long debateRoomId = 1L;
            List<DebateVote> votes = List.of(vote1, vote2, vote3);
            List<DebateJoinedUser> allDebateJoinedUser = List.of(debateJoinedUser1);


            when(debateRoomRepository.findById(debateRoomId)).thenReturn(Optional.ofNullable(debateRoom1));
            when(debateVoteRepository.findByDebateRoomId(debateRoomId)).thenReturn(votes);
            when(userRepository.findByUserName(user1.getUserName())).thenReturn(Optional.of(user1));
            when(userRepository.findByUserName(user2.getUserName())).thenReturn(Optional.of(user2));
            when(userRepository.findByUserName(user3.getUserName())).thenReturn(Optional.ofNullable(null));

            Assertions.assertThrows(RuntimeException.class, () -> debateRoomService.resultVoteInfo(debateRoomId));
        }

        @Test
        void resultVoteInfoJoinedUserException() {
            Long debateRoomId = 1L;
            List<DebateVote> votes = List.of(vote1, vote2);
            List<DebateJoinedUser> allDebateJoinedUser = List.of(debateJoinedUser1);

            when(debateJoinedUserRepository.findByDebateRoomId(debateRoomId)).thenReturn(null);
            when(debateRoomRepository.findById(debateRoomId)).thenReturn(Optional.ofNullable(debateRoom1));
            when(debateVoteRepository.findByDebateRoomId(debateRoomId)).thenReturn(votes);
            when(userRepository.findByUserName(user1.getUserName())).thenReturn(Optional.of(user1));
            when(userRepository.findByUserName(user2.getUserName())).thenReturn(Optional.of(user2));

            Assertions.assertThrows(RuntimeException.class, () -> debateRoomService.resultVoteInfo(debateRoomId));
        }

        @Test
        void resultVoteInfoJoinedUserFilterBranchTest() {
            DebateVote debateVote1 = DebateVote.createTest(1L, 1L, "name1", true, LocalDateTime.now());
            DebateVote debateVote2 = DebateVote.createTest(2L, 1L, "name2", false, LocalDateTime.now());
            DebateVote debateVote3 = DebateVote.createTest(3L, 1L, "name3", true, LocalDateTime.now());

            DebateJoinedUser duser1 = DebateJoinedUser.createTest(1L, 1L, "juser1", true);
            DebateJoinedUser duser2 = DebateJoinedUser.createTest(2L, 1L, "juser2", false);

            User filterUser1 = User.createTest(1L, "name1", "1111", "1@ex.com");
            User filterUser2 = User.createTest(2L, "name2", "1111", "1@ex.com");
            User filterUser3 = User.createTest(3L, "name3", "1111", "1@ex.com");
            User juser1 = User.createTest(3L, "juser1", "1111", "1@ex.com");
            User juser2 = User.createTest(3L, "juser2", "1111", "1@ex.com");


            Long testId = 1L;
            List<DebateVote> list = List.of(debateVote1, debateVote2, debateVote3);
            List<DebateJoinedUser> list2 = List.of(duser1, duser2);
            when(debateRoomRepository.findById(testId)).thenReturn(Optional.of(debateRoom1));
            when(debateVoteRepository.findByDebateRoomId(testId)).thenReturn(list);
            when(userRepository.findByUserName("name1")).thenReturn(Optional.of(filterUser1));
            when(userRepository.findByUserName("name2")).thenReturn(Optional.of(filterUser2));
            when(userRepository.findByUserName("name3")).thenReturn(Optional.of(filterUser3));
            when(userRepository.findByUserName("juser1")).thenReturn(Optional.of(juser1));
            when(userRepository.findByUserName("juser2")).thenReturn(Optional.of(juser2));
            when(debateJoinedUserRepository.findByDebateRoomId(testId)).thenReturn(list2);
            VoteDto voteInfo = debateRoomService.resultVoteInfo(testId);

            //testing
            Assertions.assertEquals(debateRoom1.getTitle(), voteInfo.getTitle());
            Assertions.assertEquals(debateRoom1.getTopic(), voteInfo.getTopic());
            Assertions.assertEquals(StateType.CLOSE, voteInfo.getState());
            Assertions.assertEquals(debateRoom1.getStartTime(), voteInfo.getStartTime());
            Assertions.assertEquals(debateRoom1.getDuration(), voteInfo.getDuration());
            Assertions.assertEquals(debateRoom1.getMaxUserNumber(), voteInfo.getMaxUserNumber());
        }

        @Test
        void resultVoteInfoUserCannotFindExceptionByJoinedUserRepo() {
            Long debateRoomId = 1L;
            List<DebateVote> votes = List.of(vote1, vote2);
            List<DebateJoinedUser> allDebateJoinedUser = List.of(debateJoinedUser1, debateJoinedUser3);

            when(debateJoinedUserRepository.findByDebateRoomId(debateRoomId)).thenReturn(allDebateJoinedUser);
            when(debateRoomRepository.findById(debateRoomId)).thenReturn(Optional.ofNullable(debateRoom1));
            when(debateVoteRepository.findByDebateRoomId(debateRoomId)).thenReturn(votes);
            when(userRepository.findByUserName(user1.getUserName())).thenReturn(Optional.ofNullable(user1));
            when(userRepository.findByUserName(user2.getUserName())).thenReturn(Optional.of(user2));
            when(userRepository.findByUserName(user3.getUserName())).thenReturn(Optional.ofNullable(null));

            Assertions.assertThrows(RuntimeException.class, () -> debateRoomService.resultVoteInfo(debateRoomId));

        }


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


