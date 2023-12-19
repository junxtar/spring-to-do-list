package com.sparta.springtodolist.infra.mail.exception;

import com.sparta.springtodolist.global.exception.CustomException;
import com.sparta.springtodolist.global.exception.ErrorCode;

public class ExistsEmail extends CustomException {

    public ExistsEmail(ErrorCode errorCode) {
        super(errorCode);
    }
}
