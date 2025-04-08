package com.sprta.newsfeed.controller;


import com.sprta.newsfeed.dto.CreatePostRequestDto;
import com.sprta.newsfeed.dto.PostResponseDto;
import com.sprta.newsfeed.dto.UpdatePostRequestDto;
import com.sprta.newsfeed.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@RequestBody CreatePostRequestDto requestDto) {

    PostResponseDto response = postService.createPost(requestDto);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody UpdatePostRequestDto requestDto){
        try {
            PostResponseDto responseDto = postService.updatePost(id, requestDto);
            return ResponseEntity.ok(responseDto);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("없는 게시물")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                        "status", 404,
                        "errorCode", "NOT_FOUND",
                        "message", "없는 게시물입니다."
                ));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                        "status", 400,
                        "errorCode", "BAD_REQUEST",
                        "message", "잘못된 입력입니다."
                ));
            }
        }
    }
}
