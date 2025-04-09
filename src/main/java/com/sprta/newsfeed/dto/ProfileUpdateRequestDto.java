package com.sprta.newsfeed.dto;


import lombok.Getter;

@Getter
public class ProfileUpdateRequestDto {

    private String introduction; // 수정할 소개글
    private String password;     // 비밀번호
}
