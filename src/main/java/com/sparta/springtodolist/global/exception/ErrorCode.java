package com.sparta.springtodolist.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    //user
    EXISTS_USERNAME(HttpStatus.BAD_REQUEST.value(), "U01", "이미 존재하는 유저이름입니다."),
    FILED_EXCEPTION(HttpStatus.BAD_REQUEST.value(), "U02", "회원가입 조건에 부합합니다."),
    NOT_FOUND_USERNAME(HttpStatus.NOT_FOUND.value(), "U03", "존재하지 않는 유저이름입니다."),

    //card
    CARD_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "C01", "존재하지 않는 할일 카드입니다."),
    CARD_NOT_ACCESS(HttpStatus.FORBIDDEN.value(), "C02", "할일 카드 접근 권한이 없습니다."),

    //comment
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "M01", "존재하지 않는 댓글입니다."),
    COMMENT_NOT_ACCESS(HttpStatus.FORBIDDEN.value(), "M02", "댓글 접근 권한이 없습니다."),

    //mail
    NOT_FOUND_EMAIL(HttpStatus.NOT_FOUND.value(), "T01", "존재하지 않는 이메일입니다."),
    EXPIRED_CODE(HttpStatus.BAD_REQUEST.value(), "T02", "코드가 일치하지 않습니다."),

    //file
    NOT_ALLOWED_FILE_TYPE(HttpStatus.METHOD_NOT_ALLOWED.value(), "F01", "허용하지 않는 파일 형태 입니다."),
    NOT_FOUND_FILE(HttpStatus.NOT_FOUND.value(), "F02", "존재하지 않는 파일입니다."),
    NOT_READ_TYPE(HttpStatus.INTERNAL_SERVER_ERROR.value(), "F03", "서버의 문제로 파일을 읽을 수 없습니다.");


    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
