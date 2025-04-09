package com.sprta.newsfeed.dto.Comment;

import com.sprta.newsfeed.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {

    private final Long id;

    private final String content;

    public CommentResponseDto(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public CommentResponseDto(Comment comment){
        this.id = comment.getId();
        this.content = comment.getContent();
    }


    public static CommentResponseDto commentDto(Comment comment) {
        return new CommentResponseDto(comment.getId(), comment.getContent());
    }

}
