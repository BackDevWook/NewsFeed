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

    // 공통적으로 처리할 수 있게 만든 기본 에러 응답용 생성자
    public ErrorResponse(int status, String message) {
        this.status = status;
        this.code = "E000";
        this.message = message;
    }
}
