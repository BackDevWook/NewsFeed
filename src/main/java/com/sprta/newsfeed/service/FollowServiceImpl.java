package com.sprta.newsfeed.service;

import com.sprta.newsfeed.dto.FollowCountResponseDto;
import com.sprta.newsfeed.entity.Follow;
import com.sprta.newsfeed.entity.User;
import com.sprta.newsfeed.repository.FollowRepository;
import com.sprta.newsfeed.repository.UserRepository;
import com.sprta.newsfeed.security.customerror.CustomException;
import com.sprta.newsfeed.security.customerror.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    public final FollowRepository followRepository;
    public final UserRepository userRepositroy;

    // 팔로우 하기
    @Override
    public void saveFollow(Long followingId, Long myId) {

        // 내가 팔로우 할 유저 객체 선언
        User target = userRepositroy.findById(followingId).orElseThrow(() -> new RuntimeException("팔로우할 사용자가 존재하지 않습니다."));
        User currentUser = userRepositroy.findById(myId).orElseThrow(() -> new RuntimeException("로그인한 사용자를 찾을 수 없습니다."));

        // 자기 자신 팔로우 방지하기
        if (currentUser.getId().equals(target.getId())) {
            throw new RuntimeException("자기 자신은 팔로우할 수 없습니다.");
        }
        // 중복 팔로우 방지
        if (followRepository.existsByFollowerAndFollowing(currentUser, target)) {
            throw new RuntimeException("이미 팔로우한 사용자입니다.");
        }

        followRepository.save(new Follow(currentUser, target));
    }

    // 팔로우 삭제
    @Override
    public void deleteFollow(Long followingId, Long myId) {

        // 내가 팔로우 할 유저 객체 선언
        User target = userRepositroy.findById(followingId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        User currentUser = userRepositroy.findById(myId).orElseThrow(() -> new RuntimeException("로그인한 사용자를 찾을 수 없습니다."));

        // 팔로우 관계인지 확인
        Follow follow = followRepository.findByFollowerAndFollowing(currentUser, target).orElseThrow(() -> new RuntimeException("팔로우 관계가 존재하지 않습니다."));

        followRepository.delete(follow);
    }

    // 내 팔로워/팔로잉 수 조회
    @Override
    public FollowCountResponseDto getMyCountFollowerAndFollowing(Long myId) {

        // 로그인 한 유저 정보 찾기
        User currentUser = userRepositroy.findById(myId).orElseThrow(() -> new RuntimeException("로그인한 사용자를 찾을 수 없습니다."));

        int countFollower = followRepository.countByFollowing(currentUser); // 나를 팔로워 한 사람
        int countFollowing = followRepository.countByFollower(currentUser); // 내가 팔로잉 한 사람

        return new FollowCountResponseDto(countFollower, countFollowing);
    }

    // 유저 팔로워/팔로잉 수 조회
    @Override
    public FollowCountResponseDto getUserCountFollowerAndFollowing(Long userId) {

        // 찾을 유저
        User user = userRepositroy.findById(userId).orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다."));

        int countFollower = followRepository.countByFollowing(user); // 나를 팔로워 한 사람
        int countFollowing = followRepository.countByFollower(user); // 내가 팔로잉 한 사람

        return new FollowCountResponseDto(countFollower, countFollowing);
    }


}
