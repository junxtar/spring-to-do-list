package com.sparta.springtodolist.domain.comment.exception;

import com.sparta.springtodolist.global.exception.CustomException;
import com.sparta.springtodolist.global.exception.ErrorCode;

public class CommentNotAccessException extends CustomException {

    public CommentNotAccessException(ErrorCode errorCode) {
        super(errorCode);
    }
}
