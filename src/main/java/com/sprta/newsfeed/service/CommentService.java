package com.sprta.newsfeed.service;

import com.sprta.newsfeed.dto.Comment.CommentResponseDto;
import com.sprta.newsfeed.entity.Comment;
import com.sprta.newsfeed.entity.Post;
import com.sprta.newsfeed.entity.User;
import com.sprta.newsfeed.exception.comment.NotFoundException;
import com.sprta.newsfeed.repository.CommentRepository;
import com.sprta.newsfeed.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public Comment findByIdOrElseThrow(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new NotFoundException(id + "은(는) 없는 아이디입니다."));
    }

    public CommentResponseDto save(User user, Post post, String content) {

        // 객체 생성
        Comment comment = new Comment(user, post, content);
        // 저장된 객체 반환
        Comment savedComment = commentRepository.save(comment);
        // 반환받은 객체 DTO 형태로 반환
        return new CommentResponseDto(savedComment.getId(),user.getUserName(), savedComment.getContent());
    }

    public List<CommentResponseDto> findAll() {

        return commentRepository.findAll().stream().map(CommentResponseDto::commentDto).toList();
    }

    public List<CommentResponseDto> findAllByPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."));

        List<Comment> comments = commentRepository.findByPost(post);
        return comments.stream().map(CommentResponseDto::new).toList();
    }

    @Transactional
    public void updateComment(Long id, String newContent) {
        Comment findComment = findByIdOrElseThrow(id);
        findComment.updateComment(newContent);
    }

    public void delete(Long commentId) {

        // 인가 처리

        Comment findComment = findByIdOrElseThrow(commentId);
        commentRepository.delete(findComment);
    }


    // 수정할 때 인가처리
    public boolean checkCommentOwner(Long commentId, Long userId) {
        // 댓글을 찾고 작성자가 로그인한 사용자와 일치하는지 확인
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다."));

        return comment.getUser().getId().equals(userId);
    }
}
