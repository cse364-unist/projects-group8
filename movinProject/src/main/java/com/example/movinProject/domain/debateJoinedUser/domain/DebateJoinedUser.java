package com.example.movinProject.domain.debateJoinedUser.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"debateRoomId", "userName"})})
public class DebateJoinedUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long debateRoomId;
    private String userName;
    private boolean agree;


    public static DebateJoinedUser create(Long debateRoomId, String userName, boolean agree) {
        DebateJoinedUser debateJoinedUser = new DebateJoinedUser();

        debateJoinedUser.debateRoomId = debateRoomId;
        debateJoinedUser.userName = userName;
        debateJoinedUser.agree = agree;

        return debateJoinedUser;
    }
}
