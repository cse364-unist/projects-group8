package com.example.movinProject.main.debateRoom.service;


import com.example.movinProject.domain.debateJoinedUser.domain.DebateJoinedUser;
import com.example.movinProject.domain.debateJoinedUser.repository.DebateJoinedUserRepository;
import com.example.movinProject.domain.debateRoom.domain.DebateRoom;
import com.example.movinProject.domain.debateRoom.model.StateType;
import com.example.movinProject.domain.debateRoom.repository.DebateRoomRepository;
import com.example.movinProject.domain.debateVote.domain.DebateVote;
import com.example.movinProject.domain.debateVote.repository.DebateVoteRepository;
import com.example.movinProject.main.debateRoom.dto.DebateRoomVoteDto;
import com.example.movinProject.main.debateVote.dto.Vote;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DebateRoomService {

    private DebateRoomRepository debateRoomRepository;

    private DebateVoteRepository debateVoteRepository;

    private DebateJoinedUserRepository debateJoinedUserRepository;

    public Map<String, List<DebateRoom>> getDebateRoomsGroupedByStateByMovieId(Long movieId) {
        List<DebateRoom> rooms = debateRoomRepository.findByMovieId(movieId);
        Map<String, List<DebateRoom>> groupedRooms = new HashMap<>();
        groupedRooms.put("openDebateRooms", rooms.stream()
                .filter(room -> StateType.OPEN.equals(room.getStateType()))
                .collect(Collectors.toList()));
        groupedRooms.put("voteDebateRooms", rooms.stream()
                .filter(room -> StateType.VOTE.equals(room.getStateType()))
                .collect(Collectors.toList()));
        return groupedRooms;
    }
    public DebateRoomVoteDto getDebateRoomDetails(Long id, String userName) {
        DebateRoom debateRoom = debateRoomRepository.findById(id).orElse(null);
        if (debateRoom == null) {
            return null;
        }

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

        DebateVote debateVote = debateVoteRepository.findByUserNameAndDebateRoomId(userName, id);
        dto.setVoted(debateVote.isAgree());

        DebateJoinedUser debateJoinedUser = debateJoinedUserRepository.findByUserNameAndDebateRoomId(userName, id);
        dto.setAgree(debateJoinedUser.isAgree());
        return dto;
    }

    @Transactional
    public DebateRoomVoteDto castVote(Long id, String username, boolean agree) {

        DebateVote vote = DebateVote.create(
            id, username, agree, LocalDateTime.of(2024,4,1,0,0));

        debateVoteRepository.save(vote);

        DebateRoom debateRoom = debateRoomRepository.findById(id).orElse(null);
        if (debateRoom == null) {
            return null;
        }

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

        dto.setVoted(agree);

        DebateJoinedUser debateJoinedUser = debateJoinedUserRepository.findByUserNameAndDebateRoomId(username, id);
        dto.setAgree(debateJoinedUser.isAgree());

        return dto;
    }

}
