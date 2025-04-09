package com.sprta.newsfeed.service;

import com.sprta.newsfeed.entity.Profile;
import com.sprta.newsfeed.dto.ProfileUpdateRequestDto;
import com.sprta.newsfeed.entity.User;
import com.sprta.newsfeed.repository.ProfileRepository;
import com.sprta.newsfeed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 프로필 작성
    public void createProfile(Long userId, ProfileUpdateRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));

        Profile profile = new Profile(user, requestDto.getIntroduction());
        profileRepository.save(profile);
    }

    //프로필 조회
    public Profile getProfile(Long userId){
        return profileRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저의 프로필이 없습니다."));
    }

    // 프로필 수정 이에요
    public void updateProfile(Long userId, ProfileUpdateRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("프로필 없음"));

        profile.updateIntroduction(requestDto.getIntroduction());
        profileRepository.save(profile);
    }
}
