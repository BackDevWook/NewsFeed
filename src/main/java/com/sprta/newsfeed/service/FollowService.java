package com.sprta.newsfeed.service;

import com.sprta.newsfeed.dto.follow.FollowCountResponseDto;
import com.sprta.newsfeed.dto.follow.MyFollowingAndFollowerResponseDto;
import com.sprta.newsfeed.entity.User;

import java.util.List;

public interface FollowService {

    // 1. 유저 팔로우 하기
    public void saveFollow(Long followingId, Long myId);

    // 2. 팔로잉 삭제
    public void deleteFollow(Long followingId, Long myId);

    // 3. 내 팔로워 수 카운팅 및 조회
    public FollowCountResponseDto getMyCountFollowerAndFollowing(Long myId);

    // 4. 유저 팔로워 수 카운팅 및 조회
    public FollowCountResponseDto getUserCountFollowerAndFollowing(Long userId);

    // 5. 내가 팔로잉 한 사람 보기
    public List<MyFollowingAndFollowerResponseDto> getMyFollowing(Long userId);

    // 6. 나를 팔로우 한 사람 보기
    public List<MyFollowingAndFollowerResponseDto> getMyFollower(Long userId);

}
