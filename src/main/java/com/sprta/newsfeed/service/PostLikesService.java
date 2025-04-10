package com.sprta.newsfeed.service;

import com.sprta.newsfeed.entity.Post;
import com.sprta.newsfeed.entity.PostLikes;
import com.sprta.newsfeed.entity.User;
import com.sprta.newsfeed.repository.PostLikesRepository;
import com.sprta.newsfeed.repository.PostRepository;
import com.sprta.newsfeed.repository.UserRepository;
import com.sprta.newsfeed.security.customerror.CustomException;
import com.sprta.newsfeed.security.customerror.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostLikesService {

    private final UserRepository userRepository;
    private final PostLikesRepository postLikesRepository;
    private final PostRepository postRepository;

    // 게시글 좋아요 기능
    // 게시글에 이미 좋아요가 눌렸다면 에외 발생
    public void likePost(Long userId, Long postId){
        // user 조회 (user가 없으면 예외 처리)
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 게시글 조회 (게시글 없으면 예외 처리)
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        // 이미 좋아요한 경우 예외 처리
        if (postLikesRepository.existsByUserAndPost(user, post)) {
            throw new CustomException(ErrorCode.POST_ALREADY_LIKED);        }

        postLikesRepository.save(new PostLikes(user, post));
    }

    // 게시글 좋아요 취소 기능
    // user가 게시글에 좋아욜르 눌렀는지 확인
    public void unlikePost(Long userId, Long postId) {
        // user 조회(user가 없으면 예외 처리)
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 게시글 조회(게시글이 없으면 예외 처리)
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        // 좋아요가 존재하는지 확인 (없으면 예외 처리)
        PostLikes postLike = postLikesRepository.findByUserAndPost(user, post)
                .orElseThrow(() -> new CustomException(ErrorCode.LIKE_NOT_FOUND));

        // 좋아요 삭제
        postLikesRepository.delete(postLike);
    }

    // 좋아요 수 조회
    public Long getLikeCount(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        return postLikesRepository.countByPost(post);
    }

    // 좋아요 수 기준으로 상위 게시글 조회
    public List<Post> getTopLikedPosts(int limit) {
        return postLikesRepository.findTopLikedPosts(limit);
    }
}
