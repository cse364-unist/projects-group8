package com.example.movinProject.domain.user.domain;

import com.example.movinProject.domain.debateRoom.domain.DebateRoom;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String password;

    private String email;

    private int money;

    private LocalDateTime lastAttendance;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "usertodebaterooms",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "debate_room_id")
    )
    private List<DebateRoom> joinedDebateRooms;

    public static User create(
            String userName,
            String password,
            String email

    ) {
        User user = new User();
        user.userName = userName;
        user.password = password;
        user.email = email;
        return user;
    }

    public void adjustMoney(int amount) {
        this.money += amount;  // money 필드에 직접 접근
    }
    public void subMoney(int amount) {this.money -= amount;};

}
