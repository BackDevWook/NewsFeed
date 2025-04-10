package com.sprta.newsfeed.controller;

import com.sprta.newsfeed.dto.ProfileUpdateRequestDto;
import com.sprta.newsfeed.entity.Profile;
import com.sprta.newsfeed.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileService profileService;

    // 프로필 작성
    @PostMapping("/{userId}")
    public String createProfile(@PathVariable Long userId, @RequestBody ProfileUpdateRequestDto requestDto) {
        profileService.createProfile(userId, requestDto);
        return "프로필이 성공적으로 작성되었습니다.";
    }

    // 프로필 조회
    @GetMapping("/{userId}")
    public ResponseEntity<?> getProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(profileService.getProfile(userId));
    }

    // 프로필 수정
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateProfile(
            @PathVariable Long userId,
            @RequestBody @Valid ProfileUpdateRequestDto requestDto
    ) {
        profileService.updateProfile(userId, requestDto);
        return ResponseEntity.ok("프로필이 수정되었습니다.");
    }
}
