package com.sprta.newsfeed.dto;

import lombok.Getter;

@Getter
public class FollowCountResponseDto {

    // 팔로잉, 팔로워 수
    private final int followerCount;
    private final int followingCount;

    public FollowCountResponseDto(int followerCount, int followingCount) {
        this.followerCount = followerCount;
        this.followingCount = followingCount;
    }

}
