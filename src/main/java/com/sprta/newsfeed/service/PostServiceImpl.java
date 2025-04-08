package com.sprta.newsfeed.service;

import com.sprta.newsfeed.dto.CreatePostRequestDto;
import com.sprta.newsfeed.dto.PostResponseDto;
import com.sprta.newsfeed.dto.UpdatePostRequestDto;
import com.sprta.newsfeed.entity.Post;
import com.sprta.newsfeed.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;


    @Override
    public PostResponseDto createPost(CreatePostRequestDto requestDto) {
      userRepository.findById(1L)
              .orElseThrow(()->new RuntimeException("사용자 없음"));

      Post post = new Post(requestDto.getTitle(), requestDto.getContent(), user);
      Post saved = postRepository.save(post);

      return new PostResponseDto(
              saved.getId(),
              saved.getUser().getUsername(),
              saved.getTitle(),
              saved.getContent(),
              saved.getCountComments(),
              saved.getCountLikes()
      );
    }

    @Override
    public PostResponseDto updatePost(Long id, UpdatePostRequestDto requestDto) {
    Post post = postRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("없는 게시물입니다."));
    post.updateContent(requestDto.getContent());

    return new PostResponseDto(
            post.getId(),
            post.getUser().getUsername(),
            post.getTitle(),
            post.getContent(),
            post.getCountComments(),
            post.getCountLikes()
    );
    }
}
