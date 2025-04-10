package com.sprta.newsfeed.service;

import com.sprta.newsfeed.dto.Post.PostCreateRequestDto;
import com.sprta.newsfeed.dto.Post.PostResponseDto;
import com.sprta.newsfeed.dto.Post.PostUpdateRequestDto;

import java.util.List;

public interface PostService {

    PostResponseDto createPost(PostCreateRequestDto requestDto,Long loginUserId);

    PostResponseDto updatePost(Long id, PostUpdateRequestDto requestDto);

    List<PostResponseDto> getAllPosts(int page);

    PostResponseDto getPostWithComments(Long id);

    void deletePost(Long id);
}
