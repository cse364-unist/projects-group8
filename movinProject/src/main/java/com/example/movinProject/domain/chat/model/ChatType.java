package com.example.movinProject.domain.chat.model;

public enum ChatType {
    AGREE("agree"),
    DISAGREE("disagree"),
    MODERATE("moderate");

    private final String value;

    ChatType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
