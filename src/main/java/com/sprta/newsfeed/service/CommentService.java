package com.sprta.newsfeed.service;

import com.sprta.newsfeed.dto.Comment.CommentResponseDto;
import com.sprta.newsfeed.entity.Comment;
import com.sprta.newsfeed.entity.User;
import com.sprta.newsfeed.exception.comment.NotFoundException;
import com.sprta.newsfeed.repository.CommentRepository;
import com.sprta.newsfeed.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public Comment findByIdOrElseThrow(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new NotFoundException(id + "은(는) 없는 아이디입니다."));
    }

    public CommentResponseDto save(User user, String content) {

        // 객체 생성
        Comment comment = new Comment(user, content);
        // 저장된 객체 반환
        Comment savedComment = commentRepository.save(comment);
        // 반환받은 객체 DTO 형태로 반환
        return new CommentResponseDto(savedComment.getId(),user.getUserName(), savedComment.getContent());
    }

    public List<CommentResponseDto> findAll() {

        return commentRepository.findAll().stream().map(CommentResponseDto::commentDto).toList();
    }

    @Transactional
    public void updateComment(Long id, String newContent) {

        // 인가 처리

        Comment findComment = findByIdOrElseThrow(id);
        findComment.updateComment(newContent);
    }

    public void delete(Long commentId) {

        // 인가 처리

        Comment findComment = findByIdOrElseThrow(commentId);
        commentRepository.delete(findComment);
    }
}
