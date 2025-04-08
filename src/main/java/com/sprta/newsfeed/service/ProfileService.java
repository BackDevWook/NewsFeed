package com.sprta.newsfeed.service;

import com.sprta.newsfeed.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.sprta.newsfeed.entity.Profile;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    //프로필 조회
    public Profile getProfile(Long userId){
        return profileRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저의 프로필이 없습니다."));
    }

}
