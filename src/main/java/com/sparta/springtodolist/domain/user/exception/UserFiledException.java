package com.sparta.springtodolist.domain.user.exception;

import com.sparta.springtodolist.global.exception.CustomException;
import com.sparta.springtodolist.global.exception.ErrorCode;

public class UserFiledException extends CustomException {

    public UserFiledException(ErrorCode errorCode) {
        super(errorCode);
    }
}
