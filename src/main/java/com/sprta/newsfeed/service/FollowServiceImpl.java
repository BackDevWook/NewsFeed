package com.sprta.newsfeed.service;

import com.sprta.newsfeed.dto.FollowCountResponseDto;
import com.sprta.newsfeed.entity.Follow;
import com.sprta.newsfeed.entity.User;
import com.sprta.newsfeed.repository.FollowRepository;
import com.sprta.newsfeed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    public final FollowRepository followRepository;
    public final UserRepository userRepositroy;

    // 팔로우 하기
    @Override
    public void saveFollow(Long followingId, User currentUser) {

        // 내가 팔로우 할 유저 객체 선언
        User target = userRepositroy.findById(followingId).orElseThrow(() -> new RuntimeException("팔로우할 사용자가 존재하지 않습니다."));

        // 자기 자신 팔로우 방지하기
        if (currentUser.getId().equals(target.getId())) {
            throw new RuntimeException("자기 자신은 팔로우할 수 없습니다.");
        }
        // 중복 팔로우 방지
        if (followRepository.existsByFollowerAndFollowing(currentUser, target)) {
            throw new RuntimeException("이미 팔로우한 사용자입니다.");
        }

        followRepository.save(new Follow(target, currentUser));
    }

    // 팔로우 삭제
    @Override
    public void deleteFollow(Long followingId, User currentUser) {

        // 팔로우 삭제할 유저 객체
        User target = userRepositroy.findById(followingId).orElseThrow(() -> new RuntimeException(("언팔로우할 사용자가 존재하지 않습니다.")));

        // 팔로우 관계인지 확인
        Follow follow = followRepository.findByFollowerAndFollowing(currentUser, target).orElseThrow(() -> new RuntimeException("팔로우 관계가 존재하지 않습니다."));

        followRepository.delete(follow);
    }

    // 내 팔로워/팔로잉 수 조회
    @Override
    public FollowCountResponseDto getMyCountFollowerAndFollowing(User currentUser) {

        int countFollowing = followRepository.countByFollowing(currentUser); // 팔로잉 수
        int countFollower = followRepository.countByFollower(currentUser); // 팔로워 수

        return new FollowCountResponseDto(countFollower, countFollowing);
    }

    // 유저 팔로워/팔로잉 수 조회
    @Override
    public FollowCountResponseDto getUserCountFollowerAndFollowing(Long userId) {
        User user = userRepositroy.findById(userId).orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다."));


        int countFollowing = followRepository.countByFollowing(user); // 팔로잉 수
        int countFollower = followRepository.countByFollower(user); // 팔로워 수

        return new FollowCountResponseDto(countFollower, countFollowing);
    }


}
