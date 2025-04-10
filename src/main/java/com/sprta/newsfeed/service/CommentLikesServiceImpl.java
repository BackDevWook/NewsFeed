package com.sprta.newsfeed.service;

import com.sprta.newsfeed.entity.Comment;
import com.sprta.newsfeed.entity.CommentLikes;
import com.sprta.newsfeed.entity.User;
import com.sprta.newsfeed.repository.CommentLikesRepository;
import com.sprta.newsfeed.repository.CommentRepository;
import com.sprta.newsfeed.repository.PostRepository;
import com.sprta.newsfeed.repository.UserRepository;
import com.sprta.newsfeed.security.customerror.CustomException;
import com.sprta.newsfeed.security.customerror.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentLikesServiceImpl implements CommentLikesService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CommentLikesRepository commentLikesRepository;

    // 1. 좋아요 누르기
    @Override
    public void saveLike(Long userId, Long postId, Long commentId) {

        // 해당 댓글이 있는 게시물이 존재하지 않으면 에러처리
        if(postRepository.findById(postId).isEmpty()) {
            throw new CustomException(ErrorCode.POST_NOT_FOUND);
        }

        // 댓글, 유저도 존재하지 않는다면 에러처리 하고 매핑
        User currentUser = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        // 같은 댓글에 좋아요 요청 막기
        if(commentLikesRepository.existsByUserIdAndCommentId(currentUser.getId(), commentId)) {
            throw new CustomException(ErrorCode.ALREADY_LIKE_COMMENT);
        }

        // 좋아요 DB에 유저-댓글 관계로 저장
        commentLikesRepository.save(new CommentLikes(currentUser, comment));

        // 좋아요 수 꺼내tj +1 해서 저장
        Long likesCount = comment.getLikesCount();
        comment.updateLikesCount(likesCount + 1);

        // comment 엔티티에 좋아요 수 1 늘려서 저장
        commentRepository.save(comment);

    }

    // 2. 좋아요 취소하기
    @Override
    public void deleteLike(Long userId, Long postId, Long commentId) {

        // 해당 댓글이 있는 게시물이 존재하지 않으면 에러처리
        if(postRepository.findById(postId).isEmpty()) {
            throw new CustomException(ErrorCode.POST_NOT_FOUND);
        }

        // 댓글, 유저도 존재하지 않는다면 에러처리 하고 매핑
        User currentUser = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        // DB안에 있는 객체를 찾아 매핑
        CommentLikes commentLikes = commentLikesRepository.findByUserIdAndCommentId(currentUser.getId(), comment.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_LIKE_NOT_FOUND));

        // DB 삭제
        commentLikesRepository.delete(commentLikes);

        // 좋아요 수 꺼내서 -1 해서 저장
        Long likesCount = comment.getLikesCount();
        comment.updateLikesCount(likesCount - 1);

        // comment 엔티티에 좋아요 수 1 늘려서 저장
        commentRepository.save(comment);
    }

    // 3. 댓글에 좋아요 수 보기

}
