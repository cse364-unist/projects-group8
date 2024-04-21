package com.example.movinProject.main.user.service;

import com.example.movinProject.domain.debateJoinedUser.domain.DebateJoinedUser;
import com.example.movinProject.domain.debateJoinedUser.repository.DebateJoinedUserRepository;
import com.example.movinProject.domain.debateRoom.domain.DebateRoom;
import com.example.movinProject.domain.debateRoom.repository.DebateRoomRepository;
import com.example.movinProject.domain.debateVote.domain.DebateVote;
import com.example.movinProject.domain.debateVote.repository.DebateVoteRepository;
import com.example.movinProject.domain.user.domain.User;
import com.example.movinProject.domain.user.repository.UserRepository;
import com.example.movinProject.main.debateRoom.dto.DebateRoomDto;
import com.example.movinProject.main.debateRoom.dto.DebateRoomVoteDto;
import com.example.movinProject.main.user.dto.UserDto;
import com.example.movinProject.main.user.dto.UserRegisterRequest;
import io.micrometer.core.instrument.binder.db.MetricsDSLContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
        List<DebateRoom> debateRooms = debateRoomRepository.findDebateRoomsByUserName(user.getUserName());

        List<DebateRoomVoteDto> debateRoomVoteDtos = debateRooms.stream().map(debateRoom -> {
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

            Long debateRoomId = debateRoom.getId();
            DebateVote debateVote = debateVoteRepository.findByUserNameAndDebateRoomId(username, debateRoomId);
            if (debateVote != null) {
                dto.setVoted(debateVote.isAgree());
            }

            DebateJoinedUser debateJoinedUser = debateJoinedUserRepository.findByUserNameAndDebateRoomId(username, debateRoomId);
            if (debateJoinedUser != null) {
                dto.setAgree(debateJoinedUser.isAgree());
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