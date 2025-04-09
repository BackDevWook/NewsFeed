package com.sprta.newsfeed.dto.Comment;

import lombok.Getter;

@Getter
public class CreateCommentRequestDto {

    private final String content;

    public CreateCommentRequestDto(String content){
        this.content = content;
    }
}
