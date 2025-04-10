package com.sprta.newsfeed.service;

import com.sprta.newsfeed.dto.ProfileResponseDto;
import com.sprta.newsfeed.entity.Profile;
import com.sprta.newsfeed.dto.ProfileUpdateRequestDto;
import com.sprta.newsfeed.entity.User;
import com.sprta.newsfeed.repository.ProfileRepository;
import com.sprta.newsfeed.repository.UserRepository;
import com.sprta.newsfeed.security.customerror.CustomException;
import com.sprta.newsfeed.security.customerror.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder; // 검색해봐야지

    // 프로필 작성
    public void createProfile(Long userId, ProfileUpdateRequestDto requestDto) {
        // userId로 user 정보 조회 (없으면 예외 발생)
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 이미 프로필이 존재하는지 확인
        if (profileRepository.findByUserId(userId).isPresent()) {
            throw new CustomException(ErrorCode.PROFILE_ALREADY_EXISTS);        }

        // user와 소개글을 가지고 새로운 프로필을 생성
        Profile profile = new Profile(user, requestDto.getIntroduction());

        // DB에 프로필 저장
        profileRepository.save(profile);
    }

    //프로필 조회
    public ProfileResponseDto getProfile(Long userId){
        // userId를 기준으로 프로필 조회(없으면 예외 발생)
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.PROFILE_NOT_FOUND));
        User user = profile.getUser();

        return new ProfileResponseDto(user.getUserName(), user.getEmail(), profile.getIntroduction());
    }

    // 프로필 수정
    public void updateProfile(Long userId, ProfileUpdateRequestDto requestDto) {
        //  userId로 user 정보 조회(없으면 예외 발생)
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 비밀번호 일치하는지 검증
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.MISMATCH_PASSWORD);
        }

        // userId로 해당 user 프로필 조회(없으면 예외 발생)
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.PROFILE_NOT_FOUND));


        // 프로필의 기존소개글을 수정
        profile.updateIntroduction(requestDto.getIntroduction());

        // 변경된 내용 저장
        profileRepository.save(profile);
    }
}
