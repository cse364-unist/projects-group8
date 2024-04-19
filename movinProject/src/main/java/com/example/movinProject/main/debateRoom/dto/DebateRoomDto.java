package com.example.movinProject.main.debateRoom.dto;

import com.example.movinProject.main.movie.dto.MovieDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
public class DebateRoomDto {
    private Long id;
    private String title;
    private String topic;
    private String state;
    private MovieDto movie;
    private LocalDateTime startTime;
    private int duration;
    private int maxUserNumber;
    private int agreeJoinedUserNumber;
    private int disagreeJoinedUserNumber;
}