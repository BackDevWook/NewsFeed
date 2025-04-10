package com.sprta.newsfeed.service;

import com.sprta.newsfeed.dto.PostCreateRequestDto;
import com.sprta.newsfeed.dto.PostResponseDto;
import com.sprta.newsfeed.dto.PostUpdateRequestDto;
import com.sprta.newsfeed.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {

    PostResponseDto createPost(PostCreateRequestDto requestDto,Long loginUserId);

    PostResponseDto updatePost(Long id, PostUpdateRequestDto requestDto);

    List<PostResponseDto> getAllPosts(int page);

    PostResponseDto getPostWithComments(Long id);

    void deletePost(Long id);

    Post findById(Long postId);
}
