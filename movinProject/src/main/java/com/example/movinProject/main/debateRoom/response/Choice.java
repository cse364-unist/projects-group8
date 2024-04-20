package com.example.movinProject.main.debateRoom.response;

import com.example.movinProject.main.debateRoom.model.Message;

public class Choice {
    public int index;
    public Message message;
    public String finish_reason;

    public Choice() {
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getFinish_reason() {
        return finish_reason;
    }

    public void setFinish_reason(String finish_reason) {
        this.finish_reason = finish_reason;
    }
}
