package com.sprta.newsfeed.service;

import com.sprta.newsfeed.dto.PostCreateRequestDto;
import com.sprta.newsfeed.dto.PostResponseDto;
import com.sprta.newsfeed.dto.PostUpdateRequestDto;

import java.util.List;

public interface PostService {

    PostResponseDto createPost(PostCreateRequestDto requestDto);

    PostResponseDto updatePost(Long id, PostUpdateRequestDto requestDto);

    List<PostResponseDto> getAllPosts(int page);

    void deletePost(Long id);
}
