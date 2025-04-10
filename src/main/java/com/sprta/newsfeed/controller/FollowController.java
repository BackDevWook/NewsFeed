package com.sprta.newsfeed.controller;

import com.sprta.newsfeed.common.Const;
import com.sprta.newsfeed.dto.FollowCountResponseDto;
import com.sprta.newsfeed.dto.LoginResponseDto;
import com.sprta.newsfeed.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/follows")
@RestController
public class FollowController {

    private final FollowService followService;

    // 1. 팔로우 하기
    @PostMapping("/{id}")
    public ResponseEntity<String> followUser(@PathVariable Long id, @SessionAttribute(name = Const.LOGIN_USER) LoginResponseDto dto) {

        followService.saveFollow(id, dto.getUserId());

        return ResponseEntity.ok("팔로우 성공함");
    }

    // 2. 팔로우 취소
    @DeleteMapping("/{id}")
    public ResponseEntity<String> unFollowUser(@PathVariable Long id, @SessionAttribute(name = Const.LOGIN_USER) LoginResponseDto dto) {

        followService.deleteFollow(id, dto.getUserId());

        return ResponseEntity.ok("팔로우 삭제 됌");
    }

    // 3. 내 팔로잉/팔로워 조회
    @GetMapping
    public ResponseEntity<FollowCountResponseDto> getMyFollowingAndFollower(@SessionAttribute(name = Const.LOGIN_USER) LoginResponseDto dto) {

        return ResponseEntity.ok(followService.getMyCountFollowerAndFollowing(dto.getUserId()));

    }

    // 4. 유저 팔로잉/팔로워 조회
    @GetMapping("/{id}")
    public ResponseEntity<FollowCountResponseDto> getUserFollowingAndFollower(@PathVariable Long id) {
        return ResponseEntity.ok(followService.getUserCountFollowerAndFollowing(id));
    }
}
