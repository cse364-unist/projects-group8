package com.example.movinProject.main.debateRoom.service;


import com.example.movinProject.domain.debateRoom.domain.DebateRoom;
import com.example.movinProject.domain.debateRoom.model.StateType;
import com.example.movinProject.domain.debateRoom.repository.DebateRoomRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class DebateRoomService {

    private DebateRoomRepository debateRoomRepository;


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

}
