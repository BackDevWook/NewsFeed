package com.sprta.newsfeed.dto.Comment;

import lombok.Getter;

@Getter
public class UpdateCommentRequestDto {

    private final String newContent;

    public UpdateCommentRequestDto(String newContent) {
        this.newContent = newContent;
    }
}
