package com.example.movinProject.main.debateRoom.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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
