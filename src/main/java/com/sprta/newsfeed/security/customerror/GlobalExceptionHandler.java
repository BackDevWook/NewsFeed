package com.sprta.newsfeed.security.customerror;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

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

    // @Valid, @Validated 사용 시 유효성 검사에 실패하면 발생하는 예외를 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        // 에러 메시지를 담은 ErrorResponse 객체를 생성하고, HTTP 상태 코드와 함께 응답
        return new ResponseEntity<>(
                new ErrorResponse(
                        ErrorCode.INVALID_INPUT.getStatus(),
                        ErrorCode.INVALID_INPUT.getCode(),
                        errorMessage
                ),
                HttpStatus.valueOf(ErrorCode.INVALID_INPUT.getStatus())
        );
    }

}
