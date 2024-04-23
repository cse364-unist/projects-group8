package com.example.movinProject.domain.debateVote.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import net.bytebuddy.asm.Advice;
import org.springframework.format.annotation.DateTimeFormat;

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

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
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


    public String getUserId() {
        return userName;
    }
}
