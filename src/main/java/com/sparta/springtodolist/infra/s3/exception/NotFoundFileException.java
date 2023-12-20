package com.sparta.springtodolist.infra.s3.exception;

import com.sparta.springtodolist.global.exception.CustomException;
import com.sparta.springtodolist.global.exception.ErrorCode;

public class NotFoundFileException extends CustomException {

    public NotFoundFileException(ErrorCode errorCode) {
        super(errorCode);
    }
}
