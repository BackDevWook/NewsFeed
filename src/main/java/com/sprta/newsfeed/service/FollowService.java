package com.sprta.newsfeed.service;

import com.sprta.newsfeed.dto.FollowCountResponseDto;
import com.sprta.newsfeed.entity.User;

public interface FollowService {

    // 1. 유저 팔로우 하기
    public void saveFollow(Long followingId, User currentUser);

    // 2. 팔로잉 삭제
    public void deleteFollow(Long followingId, User currentUser);

    // 3. 내 팔로워 수 카운팅 및 조회
    public FollowCountResponseDto getMyCountFollowerAndFollowing();

    // 4. 유저 팔로워 수 카운팅 및 조회
    public FollowCountResponseDto getUserCountFollowerAndFollowing(Long userId);



}
