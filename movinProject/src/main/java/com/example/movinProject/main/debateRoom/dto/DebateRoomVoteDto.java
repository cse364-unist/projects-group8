package com.example.movinProject.main.debateRoom.dto;

import com.example.movinProject.domain.chat.domain.Chat;
import com.example.movinProject.domain.debateRoom.model.StateType;
import com.example.movinProject.domain.user.domain.User;
import com.example.movinProject.main.movie.dto.MovieDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DebateRoomVoteDto {
    private boolean voted;
    private boolean agree;
    private MovieDto movie;
    private String title;
    private String topic;
    private StateType stateType;
    private LocalDateTime startTime;
    private int duration;
    private int maxUserNumber;
    private int agreeJoinedUserNumber;
    private int disagreeJoinedUserNumber;
    private String summarize;
    private Chat chat;
}