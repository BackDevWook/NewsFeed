package com.sprta.newsfeed.dto;


import lombok.Getter;

@Getter
public class PostUpdateRequestDto {

    private final String content;

    public PostUpdateRequestDto(String content) {
        this.content = content;
    }
}
