package com.example.movinProject.domain.debateRoom.domain;

import com.example.movinProject.domain.debateRoom.model.StateType;
import jakarta.persistence.*;
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

    @Enumerated(EnumType.STRING)
    private StateType stateType;

    private Long movieId;

    private LocalDateTime startTime;

    private int duration;

    private String topic;

    private int maxUserNumber;

    private String summarize;
}
