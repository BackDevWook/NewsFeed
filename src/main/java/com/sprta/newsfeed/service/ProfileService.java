package com.sprta.newsfeed.service;

import com.sprta.newsfeed.dto.ProfileUpdateRequestDto;
import com.sprta.newsfeed.entity.User;
import com.sprta.newsfeed.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.sprta.newsfeed.entity.Profile;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    //프로필 조회
    public Profile getProfile(Long userId){
        return profileRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저의 프로필이 없습니다."));
    }

    // 프로필 수정
    @Transactional
    public void updateProfile(Long userId, ProfileUpdateRequestDto requestDto) {
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("프로필이 없습니다."));

        User user = profile.getUser();

        // 비밀번호 확인
        if (!user.getPassword().equals(requestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 소개글 수정
        profile.updateIntroduction(requestDto.getIntroduction());
    }
}
