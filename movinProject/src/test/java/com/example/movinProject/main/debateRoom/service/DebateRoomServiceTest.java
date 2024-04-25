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
import com.example.movinProject.main.debateRoom.dto.*;
import com.example.movinProject.main.movie.dto.MovieDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
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

        private DebateVote vote1;
        private DebateVote vote2;
        private DebateVote vote3;

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
            vote3 = DebateVote.createTest(3L, 1L, "user3", false, now);

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

        @Test
        void getDebateRoomDetails_null01() {
            Long movieId = 1L;
            Long debateRoomId = 1L;
            String userName = "user1";
            when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie1));
            when(debateRoomRepository.findById(debateRoomId)).thenReturn(Optional.empty());
            when(debateVoteRepository.findByUserNameAndDebateRoomId(userName, debateRoomId)).thenReturn(vote1);
            when(debateJoinedUserRepository.findByUserNameAndDebateRoomId(userName, debateRoomId)).thenReturn(Optional.of(debateJoinedUser1));
            when(debateJoinedUserRepository.findByDebateRoomId(debateRoomId)).thenReturn(List.of(debateJoinedUser1));
            when(chatRepository.findByDebateRoomId(debateRoomId)).thenReturn(List.of(chat1, chat2));
            DebateRoomChatDto resultDto = debateRoomService.getDebateRoomDetails(debateRoomId, userName);

            // assertion
             Assertions.assertEquals(resultDto, null);
        }

        @Test
        void getDebateRoomDetails_Vote_false() {
            Long movieId = 1L;
            Long debateRoomId = 1L;
            String userName = "user1";
            when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie1));
            when(debateRoomRepository.findById(debateRoomId)).thenReturn(Optional.of(this.debateRoom1));
            when(debateVoteRepository.findByUserNameAndDebateRoomId(userName, debateRoomId)).thenReturn(Optional.empty());
            when(debateJoinedUserRepository.findByUserNameAndDebateRoomId(userName, debateRoomId)).thenReturn(Optional.of(debateJoinedUser1));
            when(debateJoinedUserRepository.findByDebateRoomId(debateRoomId)).thenReturn(List.of(debateJoinedUser1));
            when(chatRepository.findByDebateRoomId(debateRoomId)).thenReturn(List.of(chat1, chat2));
            DebateRoomChatDto resultDto = debateRoomService.getDebateRoomDetails(debateRoomId, userName);

            // assertion
             Assertions.assertEquals(resultDto, null);

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