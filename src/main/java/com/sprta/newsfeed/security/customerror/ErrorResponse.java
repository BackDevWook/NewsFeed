package com.sprta.newsfeed.security.customerror;

import lombok.Getter;

@Getter
public class ErrorResponse { // 에러 메시지를 JSON 형태로 전달할 DTO
    private final int status; // 상태코드
    private final String code; // 고유코드
    private final String message; // 메시지

    public ErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public ErrorResponse(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
