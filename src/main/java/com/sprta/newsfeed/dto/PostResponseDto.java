package com.sprta.newsfeed.dto;


import lombok.Getter;

@Getter
public class PostResponseDto {

    private final Long id;

    private final String userName;

    private final String title;

    private final String content;

    private final Integer commentCount;

    private final Integer likesCount;

    public PostResponseDto(Long id, String userName, String title, String content, Integer commentCount, Integer likesCount) {
        this.id = id;
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.commentCount = commentCount;
        this.likesCount = likesCount;
    }
}
