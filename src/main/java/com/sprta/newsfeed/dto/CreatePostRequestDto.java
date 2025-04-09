package com.sprta.newsfeed.dto;


import lombok.Getter;

@Getter
public class CreatePostRequestDto {

    private final String title;

    private final String content;

    public CreatePostRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
