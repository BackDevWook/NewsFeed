package com.sprta.newsfeed.dto.Comment;

import lombok.Getter;

@Getter
public class CommentWithUsernameResponseDto {

    private final String content;

    public CommentWithUsernameResponseDto(String content) {
        this.content = content;
    }
}
