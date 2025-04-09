package com.sprta.newsfeed.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FollowCountResponseDto {

    // 팔로잉, 팔로워 수
    private final int followerCount;
    private final int followingCount;

}
