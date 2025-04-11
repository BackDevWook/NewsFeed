package com.sprta.newsfeed.dto.follow;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MyFollowingAndFollowerResponseDto {

    // 팔로잉 혹은 팔로워의 이름
    private String userName;

}
