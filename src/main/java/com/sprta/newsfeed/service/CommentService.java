package com.sprta.newsfeed.service;

import com.sprta.newsfeed.dto.Comment.CommentResponseDto;
import com.sprta.newsfeed.entity.Comment;
import com.sprta.newsfeed.entity.Post;
import com.sprta.newsfeed.entity.User;
import com.sprta.newsfeed.repository.CommentRepository;
import com.sprta.newsfeed.repository.PostRepository;
import com.sprta.newsfeed.security.customerror.CustomException;
import com.sprta.newsfeed.security.customerror.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    /**
     * 댓글 ID로 댓글을 조회
     * @param id 댓글 고유 식별자 ID값
     * @return 조회한 댓글 객체
     */
    public Comment findByIdOrElseThrow(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND)); // 커스텀 에러 코드 사용
    }

    /**
     * 새로운 댓글을 생성하는 메서드
     * @param user 댓글 작성자
     * @param postId 댓글이 있는 게시물
     * @param content 댓글 내용
     * @return 댓글 정보가 담긴 DTO 반환
     */
    public CommentResponseDto save(Long postId, User user, String content) {
        // 게시물 ID를 조회하고 없으면 예외 처리
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND)); // 게시물이 없으면 예외 던짐

        // 댓글 객체 생성
        Comment comment = new Comment(user, post, content);

        // 댓글 저장
        Comment savedComment = commentRepository.save(comment);

        // 댓글 저장 후 DTO 형태로 반환
        return new CommentResponseDto(savedComment.getId(), user.getUserName(), savedComment.getContent(), savedComment.getLikesCount());
    }

    /**
     * 게시물에 있는 댓글 조회
     * @param postId 댓글 조회할 게시물 ID값
     * @return 게시물에 있는 댓글 정보를 담은 DTO 반환
     */
    public List<CommentResponseDto> findAllByPost(Long postId) {

        // 게시물 ID를 조회하고 없으면 예외처리
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        // 게시물에 있는 댓글들 반환
        List<Comment> comments = commentRepository.findByPost(post);

        // 반환된 댓글들은 comments에 담겨 CommentResponseDto로 변환되고 list에 담겨 반환됨
        return comments.stream().map(CommentResponseDto::new).toList();
    }

    /**
     * 댓글 내용을 수정하는 메서드
     * @param commentId 댓글의 고유 식별자 ID값
     * @param userId 유저의 고유 식별자 ID 값
     * @param newContent 수정한 댓글 내용
     */
    @Transactional
    public void updateComment(Long commentId, String newContent, Long userId) {
        // 댓글 작성자 여부 확인
        boolean isOwner = checkCommentOwner(commentId, userId);

        // 작성자가 아니면 예외를 던짐
        if (!isOwner) {
            throw new CustomException(ErrorCode.COMMENT_UPDATE_FORBIDDEN);
        }

        // 댓글을 조회하고, 댓글이 존재하지 않으면 예외를 던짐
        Comment comment = findByIdOrElseThrow(commentId);

        // 댓글 내용 업데이트
        comment.updateComment(newContent);
    }

    /**
     * 댓글 작성자 확인 메서드
     * @param commentId 댓글 ID
     * @param userId 로그인한 사용자 ID
     * @return 댓글 작성자인지 여부
     */
    public boolean checkCommentOwner(Long commentId, Long userId) {

        // 댓글을 찾고 작성자가 로그인한 사용자와 일치하는지 확인
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        return comment.getUser().getId().equals(userId);
    }

    /**
     * 댓글 삭제하는 메서드
     * @param commentId 삭제할 댓글의 고유 식별자 ID값
     */
    public void delete(Long commentId) {

        // 댓글을 조회하고, 댓글이 존재하지 않으면 예외를 던짐
        Comment findComment = findByIdOrElseThrow(commentId);

        // 댓글 삭제
        commentRepository.delete(findComment);
    }
}
