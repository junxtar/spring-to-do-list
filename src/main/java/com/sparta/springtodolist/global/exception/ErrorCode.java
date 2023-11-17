package com.sparta.springtodolist.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    EXISTS_USERNAME(400, "E01", "이미 존재하는 유저이름 입니다."),
    FILED_EXCEPTION(400, "F01", "회원가입 조건에 부합합니다."),
    NOT_FOUND_USERNAME(404, "U01", "존재하지 않는 유저이름 입니다.");

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
