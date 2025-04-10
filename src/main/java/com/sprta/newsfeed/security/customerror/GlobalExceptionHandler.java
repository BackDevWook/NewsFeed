package com.sprta.newsfeed.security.customerror;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // 컨트롤러 전체에서 발생하는 예외를 가로챌거에요
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handlerCustomException(CustomException ex) { // JSON 응답을 위해 ResponseEntity로 반환
        ErrorCode errorCode = ex.getErrorCode(); // 에러 코드 가져오기
        return new ResponseEntity<>(new ErrorResponse(errorCode), HttpStatus.valueOf(errorCode.getStatus())); //
    }

    // 기본 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ex.printStackTrace(); // 디버깅용 로그
        return new ResponseEntity<>(
                new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    // 유효성 검사 실패 예외 처리 (@Valid 실패 시)
    // 즉, "title" : "" <- @NotBlank(message = "제목은 필수입니다.") 내용이 비었을 때 사용하는 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse("잘못된 요청입니다.");

        return new ResponseEntity<>(
                new ErrorResponse(ErrorCode.BAD_REQUEST.getStatus(), errorMessage),
                HttpStatus.BAD_REQUEST
        );
    }

}
