package com.sprta.newsfeed.controller;

import com.sprta.newsfeed.dto.CreateCommentRequestDto;
import com.sprta.newsfeed.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts/{id}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody CreateCommentRequestDto requestDto) {

        commentService.save(requestDto.get)

    }

}
