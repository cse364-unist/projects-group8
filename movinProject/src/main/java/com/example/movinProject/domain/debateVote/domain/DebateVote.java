package com.example.movinProject.domain.debateVote.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import net.bytebuddy.asm.Advice;

@Entity
@NoArgsConstructor
@Getter
public class DebateVote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long debateRoomId;

    private String userName;

    private boolean agree;

    private LocalDateTime date;

    public static DebateVote create(
            Long debateRoomId,
            String userName,
            boolean agree,
            LocalDateTime date
    ) {
        DebateVote debateVote = new DebateVote();
        debateVote.debateRoomId = debateRoomId;
        debateVote.userName = userName;
        debateVote.agree = agree;
        debateVote.date = date;

        return debateVote;
    };
}
