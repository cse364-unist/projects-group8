package com.example.movinProject.domain.debateRoom.domain;

import com.example.movinProject.domain.debateRoom.model.StateType;
import com.example.movinProject.domain.user.domain.User;
import com.example.movinProject.main.debateRoom.dto.DebateRoomCreateDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
    private LocalDateTime startTime;

    private int duration;

    private int maxUserNumber;

    private int totalMoney;

    private String summarize;

    public static DebateRoom init(String title, String topic, StateType stateType, LocalDateTime startTime, Long movieId){
        DebateRoom debateRoom = new DebateRoom();
        debateRoom.title = title;
        debateRoom.topic = topic;
        debateRoom.stateType =stateType;
        debateRoom.startTime = startTime;
        debateRoom.movieId = movieId;
        debateRoom.duration = 20;
        debateRoom.maxUserNumber = 10;
        return debateRoom;
    }

    public static DebateRoom initTest(Long id, String title, String topic, StateType stateType, LocalDateTime startTime, Long movieId){
        DebateRoom debateRoom = new DebateRoom();
        debateRoom.id = id;
        debateRoom.title = title;
        debateRoom.topic = topic;
        debateRoom.stateType =stateType;
        debateRoom.startTime = startTime;
        debateRoom.movieId = movieId;
        return debateRoom;
    }

    public void addTotleMoney(int money) {this.totalMoney += money;};

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getTopic() {
        return topic;
    }
    public void setTopic(String topic) {
        this.topic = topic;
    }

    public StateType getStateType() {
        return stateType;
    }
    public void setStateType(StateType stateType) {
        this.stateType = stateType;
    }

    public Long getMovieId() {
        return movieId;
    }
    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getMaxUserNumber() {
        return maxUserNumber;
    }
    public void setMaxUserNumber(int maxUserNumber) {
        this.maxUserNumber = maxUserNumber;
    }


    public String getSummarize() {
        return summarize;
    }
    public void setSummarize(String summarize) {
        this.summarize = summarize;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }

    public int getTotalMoney() {
        return totalMoney;
    }
}
