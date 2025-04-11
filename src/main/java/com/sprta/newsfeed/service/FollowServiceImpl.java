package com.sprta.newsfeed.service;

import com.sprta.newsfeed.dto.follow.FollowCountResponseDto;
import com.sprta.newsfeed.dto.follow.MyFollowingAndFollowerResponseDto;
import com.sprta.newsfeed.entity.Follow;
import com.sprta.newsfeed.entity.User;
import com.sprta.newsfeed.repository.FollowRepository;
import com.sprta.newsfeed.repository.UserRepository;
import com.sprta.newsfeed.security.customerror.CustomException;
import com.sprta.newsfeed.security.customerror.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    public final FollowRepository followRepository;
    public final UserRepository userRepository;

    // 팔로우 하기
    @Override
    public void saveFollow(Long followingId, Long myId) {

        // 내가 팔로우 할 유저 객체 선언
        User target = userRepository.findById(followingId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        User currentUser = userRepository.findById(myId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 자기 자신 팔로우 방지하기
        if (currentUser.getId().equals(target.getId())) {
            throw new CustomException(ErrorCode.CANNOT_FOLLOW_SELF);
        }
        // 중복 팔로우 방지
        if (followRepository.existsByFollowerAndFollowing(currentUser, target)) {
            throw new CustomException(ErrorCode.ALREADY_FOLLOWING);
        }

        followRepository.save(new Follow(currentUser, target));
    }

    // 팔로우 삭제
    @Override
    public void deleteFollow(Long followingId, Long myId) {

        // 내가 팔로우 할 유저 객체 선언
        User target = userRepository.findById(followingId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        User currentUser = userRepository.findById(myId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 팔로우 관계인지 확인
        Follow follow = followRepository.findByFollowerAndFollowing(currentUser, target).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOLLOWING_USER));

        followRepository.delete(follow);
    }

    // 내 팔로워/팔로잉 수 조회
    @Override
    public FollowCountResponseDto getMyCountFollowerAndFollowing(Long myId) {

        // 로그인 한 유저 정보 찾기
        User currentUser = userRepository.findById(myId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        int countFollower = followRepository.countByFollowing(currentUser); // 나를 팔로워 한 사람
        int countFollowing = followRepository.countByFollower(currentUser); // 내가 팔로잉 한 사람

        return new FollowCountResponseDto(countFollower, countFollowing);
    }

    // 유저 팔로워/팔로잉 수 조회
    @Override
    public FollowCountResponseDto getUserCountFollowerAndFollowing(Long userId) {

        // 찾을 유저
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        int countFollower = followRepository.countByFollowing(user); // 나를 팔로워 한 사람
        int countFollowing = followRepository.countByFollower(user); // 내가 팔로잉 한 사람

        return new FollowCountResponseDto(countFollower, countFollowing);
    }

    // 내가 팔로잉한 사람 보기
    @Override
    public List<MyFollowingAndFollowerResponseDto> getMyFollowing(Long userId) {

        User currentUser = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        List<MyFollowingAndFollowerResponseDto> list = followRepository.findAll().stream() // 스트림 열고
                .filter(follow -> follow.getFollower().getId().equals(currentUser.getId())) // 팔로워 id가 현재 유저와 동일한 데이터를 찾아서
                .map(myFollowing -> new MyFollowingAndFollowerResponseDto(myFollowing.getFollowing().getUserName())) // 해당 데이터가 Following_id 및 이름을 추출
                .toList(); // 리스트로 반환

        if(list.isEmpty()) { // 팔로우 정보가 있는지 확인
            throw new CustomException(ErrorCode.FOLLOW_NOT_FOUND);
        }

        return list;

    }

    // 나를 팔로우한 사람 보기
    @Override
    public List<MyFollowingAndFollowerResponseDto> getMyFollower(Long userId) {

        User currentUser = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        List<MyFollowingAndFollowerResponseDto> list =  followRepository.findAll().stream() // 스트림 열고
                .filter(follow -> follow.getFollowing().getId().equals(currentUser.getId())) // 팔로잉 id가 현재 유저와 동일한 데이터를 찾아서
                .map(myFollower -> new MyFollowingAndFollowerResponseDto(myFollower.getFollower().getUserName())) // 해당 데이터의 follower_id 값 및 이름을 추출
                .toList(); // 리스트로 반환

        if(list.isEmpty()) { // 팔로우 정보가 있는지 확인
            throw new CustomException(ErrorCode.FOLLOW_NOT_FOUND);
        }

        return list;
    }





}
