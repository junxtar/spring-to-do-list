package com.sparta.springtodolist.global.dto;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private String message;
    private int code;

    public ApiResponse(boolean success, T data, String message, int code) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.code = code;
    }

    // 정적 팩토리 메서드
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, "요청 성공", 200);
    }

    public static <T> ApiResponse<T> error(String message, int code) {
        return new ApiResponse<>(false, null, message, code);
    }
}