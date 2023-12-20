package com.sparta.springtodolist.infra.s3.exception;

import com.sparta.springtodolist.global.exception.CustomException;
import com.sparta.springtodolist.global.exception.ErrorCode;

public class FileTypeNotAllowedException extends CustomException {

    public FileTypeNotAllowedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
