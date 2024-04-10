package com.example.movinProject.domain.debateRoom.model;

public enum StateType {
    OPEN("open"),
    DISCUSS("discuss"),
    VOTE("vote"),
    CLOSE("close");

    private final String value;

    StateType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
