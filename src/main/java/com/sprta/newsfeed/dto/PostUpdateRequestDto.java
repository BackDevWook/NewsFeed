package com.sprta.newsfeed.dto;


import lombok.Getter;

@Getter
//@AllArgsConstructor는 RequestDto에서 사용하지 않음
public class PostUpdateRequestDto {

    private final String content;

    public PostUpdateRequestDto(String content) {
        this.content = content;
    }
}
