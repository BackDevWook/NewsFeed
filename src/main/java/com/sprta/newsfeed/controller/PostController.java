package com.sprta.newsfeed.controller;


import com.sprta.newsfeed.dto.PostCreateRequestDto;
import com.sprta.newsfeed.dto.PostResponseDto;
import com.sprta.newsfeed.dto.PostUpdateRequestDto;
import com.sprta.newsfeed.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostCreateRequestDto requestDto) {

        PostResponseDto response = postService.createPost(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePost(@PathVariable Long id, @RequestBody PostUpdateRequestDto requestDto) {
        postService.updatePost(id, requestDto);
        return ResponseEntity.ok("게시글 수정 완료");
    }

    @GetMapping
    public List<PostResponseDto> getAllPost(@RequestParam int page) {
        return postService.getAllPosts(page);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok("게시글 삭제 완료");
    }


}
