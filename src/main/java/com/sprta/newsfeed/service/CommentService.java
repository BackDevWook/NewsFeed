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

        // 댓글 수 증가
        post.increaseCommentCount();

        // post 객체에 변경된 댓글 수 저장
        postRepository.save(post);

        // 댓글 저장 후 DTO 형태로 반환
        return new CommentResponseDto(savedComment.getId(), user.getUserName(), savedComment.getContent());
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
        // 댓글 찾기
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        // 댓글 작성자만 수정 가능함 작성자가 아니면 예외 처리
        if (!comment.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.COMMENT_UPDATE_FORBIDDEN);
        }

        // 댓글 내용 수정
        comment.updateComment(newContent);
    }

    /**
     * 댓글 삭제하는 메서드
     * @param commentId 삭제할 댓글의 고유 식별자 ID값
     */
    @Transactional
    public void delete(Long commentId, Long userId) {

        // 댓글 ID로 댓글 조회, 없으면 예외 발생
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        // 댓글 작성자인지 확인
        boolean isCommentOwner = comment.getUser().getId().equals(userId);
        // 게시글 작성자인지 확인
        boolean isPostOwner = comment.getPost().getUser().getId().equals(userId);
        // 댓글 작성자 혹은 게시글 작성자가 아니라면 삭제 권한 없음 예외 발생
        if (!isCommentOwner && !isPostOwner) {
            throw new CustomException(ErrorCode.COMMENT_DELETE_FORBIDDEN);
        }

        // 댓글이 속한 게시글 가져오기
        Post post = comment.getPost();
        // 게시글의 댓글 수 감소
        post.decreaseCommentCount();
        // 댓글 삭제
        commentRepository.delete(comment);
    }
}
