package com.sprta.newsfeed.controller;


import com.sprta.newsfeed.common.Const;
import com.sprta.newsfeed.dto.LoginResponseDto;
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
    //로그인 유저 정보 받아와서 게시글 생성
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostCreateRequestDto requestDto,
                                                      @SessionAttribute(name = Const.LOGIN_USER) LoginResponseDto dto) {

        PostResponseDto response = postService.createPost(requestDto, dto.getUserId());

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

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long id){
        PostResponseDto response = postService.getPostWithComments(id);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok("게시글 삭제 완료");
    }


}
