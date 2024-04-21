package com.example.movinProject.main.user.dto;

import com.example.movinProject.main.debateRoom.dto.DebateRoomVoteDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private List<DebateRoomVoteDto> joinedDebateRooms;
    private int money;
    private LocalDateTime lastAttendance;
}