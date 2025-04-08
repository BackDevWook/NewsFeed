package com.sprta.newsfeed.service;

import com.sprta.newsfeed.dto.CommentResponseDto;
import com.sprta.newsfeed.entity.Comment;
import com.sprta.newsfeed.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public Comment findByIdOrElseThrow(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, id + "는 없는 아이디입니다."));
    }

    public User findUserByUsernameOrElseThrow(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, username + "은(는) 없는 이름입니다."));
    }

    public ResponseEntity save(String content) {

        // 사용자 정보 추가
        User findUsername = findUserByUsernameOrElseThrow(username);

        // 객체 생성
        Comment comment = new Comment(content);
        comment.setUser(findUsername);

        // 저장된 객체 반환
        Comment savedComment = commentRepository.save(comment);

        // 반환받은 객체 DTO 형태로 반환
        return new CommentResponseDto(savedComment.getId(), savedComment.getUsername(), savedComment.getContent());


    }



}
