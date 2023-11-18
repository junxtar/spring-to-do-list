package com.sparta.springtodolist.domain.card.exception;

import com.sparta.springtodolist.global.exception.CustomException;
import com.sparta.springtodolist.global.exception.ErrorCode;

public class CardNotAccessException extends CustomException {

    public CardNotAccessException(ErrorCode errorCode) {
        super(errorCode);
    }
}
