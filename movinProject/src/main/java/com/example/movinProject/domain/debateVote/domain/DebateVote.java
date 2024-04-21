package com.example.movinProject.domain.debateVote.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class DebateVote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long debateRoomId;

    private Long userId;

    private boolean agree;

    private LocalDateTime date;

    public boolean getAgree() {
        return agree;
    }

    public Long getUserId() {
        return userId;
    }
}
