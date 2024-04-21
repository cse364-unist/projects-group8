package com.example.movinProject.main.debateRoom.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class DebateRoomCreateDto {
    String title;
    String topic;
    LocalDateTime startTime;
    Long movieId;
}
