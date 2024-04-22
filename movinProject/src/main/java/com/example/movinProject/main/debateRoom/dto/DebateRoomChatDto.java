package com.example.movinProject.main.debateRoom.dto;

import com.example.movinProject.domain.chat.domain.Chat;
import com.example.movinProject.domain.debateRoom.model.StateType;
import com.example.movinProject.main.movie.dto.MovieDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DebateRoomChatDto {
    private boolean voted; // 투표했는가
    private boolean voteAgree; // 어디로 투표했는가
    private boolean joined; // 참여했는가
    private boolean agree; // 어느 편으로 join했는가
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
    private List<Chat> chats;
}