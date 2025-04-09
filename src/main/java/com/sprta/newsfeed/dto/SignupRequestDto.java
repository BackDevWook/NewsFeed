package com.sprta.newsfeed.dto;

import lombok.Getter;

@Getter
public class SignupRequestDto {
    private String username;
    private String email;
    private String password;

    public SignupRequestDto(String username,String email,String password ) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
