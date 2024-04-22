package com.example.movinProject.main.user.service;

import com.example.movinProject.domain.debateJoinedUser.domain.DebateJoinedUser;
import com.example.movinProject.domain.debateJoinedUser.repository.DebateJoinedUserRepository;
import com.example.movinProject.domain.debateRoom.domain.DebateRoom;
import com.example.movinProject.domain.debateRoom.repository.DebateRoomRepository;
import com.example.movinProject.domain.debateVote.domain.DebateVote;
import com.example.movinProject.domain.debateVote.repository.DebateVoteRepository;
import com.example.movinProject.domain.movie.domain.Movie;
import com.example.movinProject.domain.movie.repository.MovieRepository;
import com.example.movinProject.domain.user.domain.User;
import com.example.movinProject.domain.user.repository.UserRepository;
import com.example.movinProject.main.debateRoom.dto.DebateRoomVoteDto;
import com.example.movinProject.main.movie.dto.MovieDto;
import com.example.movinProject.main.user.dto.UserDto;
import com.example.movinProject.main.user.dto.UserRegisterRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DebateRoomRepository debateRoomRepository;

    @Autowired
    private DebateJoinedUserRepository debateJoinedUserRepository;

    @Autowired
    private DebateVoteRepository debateVoteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MovieRepository movieRepository;

    @Transactional
    public Optional<UserDto> registerUser(UserRegisterRequest request) {
        if (userRepository.existsByUserName(request.getUserName())) {
            return Optional.empty();
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User newUser = User.create(request.getUserName(), encodedPassword,request.getEmail());
        userRepository.save(newUser);

        UserDto userDto = new UserDto();
        userDto.setId(newUser.getId());
        userDto.setName(newUser.getUserName());
        userDto.setMoney(0);
        userDto.setLastAttendance(LocalDateTime.of(2024, 4, 1, 0, 0));

        return Optional.of(userDto);
    }

    public UserDto getUserDetails(String username) {
        User user = userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<Long> debateRoomIds = debateJoinedUserRepository.findIdsByUserName(username);
        List<DebateRoom> debateRooms = debateRoomRepository.findAllById(debateRoomIds);

        List<DebateRoomVoteDto> debateRoomVoteDtos = debateRooms.stream().map(debateRoom -> {
            Movie movie = movieRepository.findById(debateRoom.getMovieId()).orElseThrow();
            MovieDto movieDto = MovieDto.builder()
                    .id(movie.getId())
                    .name(movie.getTitle())
                    .thumbnailUrl(movie.getThumbnailUrl())
                    .build();


            DebateRoomVoteDto dto = new DebateRoomVoteDto();
            dto.setTitle(debateRoom.getTitle());
            dto.setTopic(debateRoom.getTopic());
            dto.setStateType(debateRoom.getStateType());
            dto.setStartTime(debateRoom.getStartTime());
            dto.setDuration(debateRoom.getDuration());
            dto.setMaxUserNumber(debateRoom.getMaxUserNumber());
            dto.setAgreeJoinedUserNumber(debateRoom.getAgreeJoinedUserNumber());
            dto.setDisagreeJoinedUserNumber(debateRoom.getDisagreeJoinedUserNumber());
            dto.setSummarize(debateRoom.getSummarize());
            dto.setMovie(movieDto);
            Long debateRoomId = debateRoom.getId();
            DebateVote debateVote = debateVoteRepository.findByUserNameAndDebateRoomId(username, debateRoomId);
            if (debateVote != null) {
                dto.setVoted(true);
                dto.setVoteAgree(debateVote.isAgree());
            }else{
                dto.setVoted(false);
                dto.setVoteAgree(false);
            }

            Optional<DebateJoinedUser> debateJoinedUser = debateJoinedUserRepository.findByUserNameAndDebateRoomId(username, debateRoomId);
            if (debateJoinedUser.isPresent()) {
                dto.setJoined(true);
                dto.setAgree(debateJoinedUser.get().isAgree());
            }else {
                dto.setJoined(false);
                dto.setAgree(false);
            }

            return dto;
        }).toList();

        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setName(user.getUserName());
        userDto.setJoinedDebateRooms(debateRoomVoteDtos);
        userDto.setMoney(user.getMoney());
        userDto.setLastAttendance(user.getLastAttendance());


        return userDto;
    }


}