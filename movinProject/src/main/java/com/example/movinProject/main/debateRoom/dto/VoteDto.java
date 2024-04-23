package com.example.movinProject.main.debateRoom.dto;

import com.example.movinProject.domain.chat.domain.Chat;
import com.example.movinProject.domain.debateRoom.domain.DebateRoom;
import com.example.movinProject.domain.debateRoom.model.StateType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.swing.plaf.nimbus.State;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public class VoteDto {
    private boolean vote;
    private boolean agree;
    private String title;
    private String topic;
    private StateType state;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
    private LocalDateTime startTime;

    private int duration;
    private int maxUserNumber;
    private int agreeJoinedUserNumber;
    private int disagreeJoinedUserNumber;
    private String summarize;
    private List<Chat> chats;

    public boolean isVote() {
        return vote;
    }

    public void setVote(boolean vote) {
        this.vote = vote;
    }

    public boolean isAgree() {
        return agree;
    }
    public void setAgree(boolean agree) {
        this.agree = agree;
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

    public StateType getState() {
        return state;
    }
    public void setState(StateType state) {
        this.state = state;
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

    public List<Chat> getChats() {
        return chats;
    }
    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }


}