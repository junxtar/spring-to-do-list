package com.sparta.springtodolist.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    EXISTS_USERNAME(HttpStatus.BAD_REQUEST.value(), "U01", "이미 존재하는 유저이름 입니다."),
    FILED_EXCEPTION(HttpStatus.BAD_REQUEST.value(), "U02", "회원가입 조건에 부합합니다."),
    NOT_FOUND_USERNAME(HttpStatus.NOT_FOUND.value(), "U03", "존재하지 않는 유저이름 입니다."),
    CARD_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "C01", "존재하지 않는 할 일 카드 입니다.");

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
