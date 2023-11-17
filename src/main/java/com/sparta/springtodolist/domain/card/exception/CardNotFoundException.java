package com.sparta.springtodolist.domain.card.exception;

import com.sparta.springtodolist.global.exception.CustomException;
import com.sparta.springtodolist.global.exception.ErrorCode;

public class CardNotFoundException extends CustomException {

    public CardNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}

