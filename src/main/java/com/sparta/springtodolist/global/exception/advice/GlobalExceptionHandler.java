package com.sparta.springtodolist.global.exception.advice;

import com.sparta.springtodolist.global.exception.CustomException;
import com.sparta.springtodolist.global.exception.ErrorCode;
import com.sparta.springtodolist.global.exception.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<Object> handleCustomException(CustomException e) {
        log.info(e.getMessage(), e);
        ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status,
        WebRequest request) {
        ErrorCode errorCode = ErrorCode.FILED_EXCEPTION;
        return handleExceptionInternal(errorCode, ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getStatus())
            .body(makeErrorResponse(errorCode));
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode) {
        return ErrorResponse.builder()
            .code(errorCode.getCode())
            .message(errorCode.getMessage())
            .build();
    }
    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode, String message) {
        return ResponseEntity.status(errorCode.getStatus())
            .body(makeErrorResponse(errorCode, message));
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode, String message) {
        return ErrorResponse.builder()
            .code(errorCode.name())
            .message(message)
            .build();
    }
}
