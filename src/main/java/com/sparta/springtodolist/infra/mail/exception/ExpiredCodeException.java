package com.sparta.springtodolist.infra.mail.exception;

import com.sparta.springtodolist.global.exception.CustomException;
import com.sparta.springtodolist.global.exception.ErrorCode;

public class ExpiredCodeException extends CustomException {

    public ExpiredCodeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
