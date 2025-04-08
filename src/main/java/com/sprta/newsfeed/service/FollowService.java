package com.sprta.newsfeed.service;

public interface FollowService {

    // 1. 팔로잉 추가
    public void saveFollowing();
    // 2. 팔로잉 삭제
    public void deleteFollowing();
    // 3. 내 팔로워 수 카운팅 및 조회
    public void getMyCountFollower();
    // 4. 유저 팔로워 수 카운팅 및 조회
    public void getUserCountFollower();
}
