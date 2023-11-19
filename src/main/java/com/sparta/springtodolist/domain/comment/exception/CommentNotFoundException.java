package com.sparta.springtodolist.domain.comment.exception;

import com.sparta.springtodolist.global.exception.CustomException;
import com.sparta.springtodolist.global.exception.ErrorCode;

public class CommentNotFoundException extends CustomException {

    public CommentNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
