package com.example.movinProject.main.debateRoom.dto;

import com.example.movinProject.domain.chat.Chat;
import com.example.movinProject.main.movie.dto.MovieDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
public class DebateRoomDto {
    private Long id;
    private String title;
    private String topic;
    private String state;
    private MovieDto movie;
    private LocalDateTime startTime;
    private int duration;
    private int maxUserNumber;
    private int agreeJoinedUserNumber;
    private int disagreeJoinedUserNumber;
}

class VoteDto {
    private boolean vote;
    private boolean agree;
    private String title;
    private String topic;
    private String state;
    private String startTime;
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

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
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