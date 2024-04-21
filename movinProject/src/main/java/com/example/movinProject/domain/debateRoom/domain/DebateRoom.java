package com.example.movinProject.domain.debateRoom.domain;

import com.example.movinProject.domain.debateRoom.model.StateType;
import com.example.movinProject.domain.user.domain.User;
import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class DebateRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String topic;

    @Enumerated(EnumType.STRING)
    private StateType stateType;

    private Long movieId;

    private LocalDateTime startTime;

    private int duration;

    private int maxUserNumber;

    private int agreeJoinedUserNumber;

    private int disagreeJoinedUserNumber;

    private String summarize;

    @ManyToMany(mappedBy = "joinedDebateRooms")
    private List<User> participants;
}
