package com.sprta.newsfeed.dto;

import lombok.Getter;

@Getter
public class LoginResponseDto {
    private final String email;
    private final String password;
    // 이외 응답에 필요한 데이터들을 필드로 구성하면 된다.
    // 필요한 생성자

    public LoginResponseDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}