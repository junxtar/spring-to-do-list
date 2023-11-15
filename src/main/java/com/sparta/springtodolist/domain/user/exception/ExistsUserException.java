package com.sparta.springtodolist.domain.user.exception;

import com.sparta.springtodolist.global.exception.CustomException;
import com.sparta.springtodolist.global.exception.ErrorCode;

public class ExistsUserException extends CustomException {

    public ExistsUserException(ErrorCode errorCode) {
        super(errorCode);
    }
}
