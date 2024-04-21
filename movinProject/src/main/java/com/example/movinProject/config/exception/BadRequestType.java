package com.example.movinProject.config.exception;

import lombok.Getter;

@Getter
public enum BadRequestType {
    EXISTS_USER("이미 존재하는 사용자 id입니다."),
    CANNOT_FIND_USER("존재하지 않는 유저입니다."),
    CANNOT_FIND_DEBATE_ROOM("존재하지 않는 토론방입니다");
    ;

    private final String message;

    BadRequestType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}