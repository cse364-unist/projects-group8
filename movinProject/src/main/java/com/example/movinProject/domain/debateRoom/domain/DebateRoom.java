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

    public int getAgreeJoinedUserNumber() {
        return agreeJoinedUserNumber;
    }
    public void setAgreeJoinedUserNumber(int agreeJoinedUserNumber) {
        this.agreeJoinedUserNumber = agreeJoinedUserNumber;
    }

    public int getDisagreeJoinedUserNumber() {
        return disagreeJoinedUserNumber;
    }
    public void setDisagreeJoinedUserNumber(int disagreeJoinedUserNumber) {
        this.disagreeJoinedUserNumber = disagreeJoinedUserNumber;
    }

    public String getSummarize() {
        return summarize;
    }
    public void setSummarize(String summarize) {
        this.summarize = summarize;
    }
}
