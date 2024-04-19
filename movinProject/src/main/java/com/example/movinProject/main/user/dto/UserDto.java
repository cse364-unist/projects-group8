package com.example.movinProject.main.user.dto;

import com.example.movinProject.main.debateRoom.dto.DebateRoomDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class UserDto {
    private Long id;
    private String name;
    private List<DebateRoomDto> joinedDebateRooms;
    private int money;
    private LocalDateTime lastAttendance;
}