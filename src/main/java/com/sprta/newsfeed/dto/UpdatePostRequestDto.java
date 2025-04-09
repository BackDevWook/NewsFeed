package com.sprta.newsfeed.dto;


import lombok.Getter;

@Getter
public class UpdatePostRequestDto {

    private final String content;

    public UpdatePostRequestDto(String content) {
        this.content = content;
    }
}
