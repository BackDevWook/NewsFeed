package com.sprta.newsfeed.controller;

import com.sprta.newsfeed.dto.FollowCountResponseDto;
import com.sprta.newsfeed.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follows")
public class FollowController {


    // 1. 팔로우 하기
    @PostMapping("/{id}")
    public ResponseEntity<String> followUser(@PathVariable Long id, @SessionAttribute(name = Const.LOGIN_USER) User currentUser) {
        return null;
    }

    // 2. 팔로우 취소
    @DeleteMapping("/{id}")
    public ResponseEntity<String> unFollowUser(@PathVariable Long id, @SessionAttribute(name = Const.LOGIN_USER) User currentUser) {
        return null;
    }

    // 3. 내 팔로잉/팔로워 조회
    @GetMapping
    public ResponseEntity<FollowCountResponseDto> getMyFollowingAndFollower(@SessionAttribute(name = Const.LOGIN_USER) User currentUser) {
        return null;
    }

    // 4. 유저 팔로잉/팔로워 조회
    @GetMapping("/{id}")
    public ResponseEntity<FollowCountResponseDto> getUserFollowingAndFollower(@PathVariable Long id) {
        return null;
    }
}
