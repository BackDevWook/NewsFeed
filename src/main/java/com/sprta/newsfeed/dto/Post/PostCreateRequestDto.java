package com.sprta.newsfeed.dto.Post;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class PostCreateRequestDto {

    private final String title;

    private final String content;

}
