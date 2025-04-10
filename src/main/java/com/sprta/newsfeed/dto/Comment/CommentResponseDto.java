package com.sprta.newsfeed.dto.Comment;

import com.sprta.newsfeed.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {

    private final Long id;

    private final String userName;

    private final String content;

    public CommentResponseDto(Long id, String userName, String content) {
        this.id = id;
        this.userName = userName;
        this.content = content;

    }

    public CommentResponseDto(Comment comment){
        this.id = comment.getId();
        this.userName = comment.getUser().getUserName();
        this.content = comment.getContent();

    }

}
