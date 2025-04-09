package com.sprta.newsfeed.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class PostUpdateRequestDto {

    private final String content;

}
