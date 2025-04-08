package com.sprta.newsfeed.service;

import com.sprta.newsfeed.dto.CreatePostRequestDto;
import com.sprta.newsfeed.dto.PostResponseDto;
import com.sprta.newsfeed.dto.UpdatePostRequestDto;

import java.util.List;

public interface PostService {

    PostResponseDto createPost(CreatePostRequestDto requestDto);

    PostResponseDto updatePost(Long id,UpdatePostRequestDto requestDto);

    List<PostResponseDto> getAllPosts(int page);

    void deletePost(Long id);
}
