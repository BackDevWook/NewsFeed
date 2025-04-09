package com.sprta.newsfeed.service;

import com.sprta.newsfeed.dto.CreatePostRequestDto;
import com.sprta.newsfeed.dto.PostResponseDto;
import com.sprta.newsfeed.dto.UpdatePostRequestDto;
import com.sprta.newsfeed.entity.Post;
import com.sprta.newsfeed.entity.User;
import com.sprta.newsfeed.repository.PostRepository;
import com.sprta.newsfeed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor //final로 선언된 필드에 대해 생성자 자동생성
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;


    @Override
    public PostResponseDto createPost(CreatePostRequestDto requestDto) {
      User user = userRepository.findById(1L)
              .orElseThrow(()->new RuntimeException("사용자 없음"));

      Post post = new Post(requestDto.getTitle(), requestDto.getContent(),user);
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

    @Override
    public List<PostResponseDto> getAllPosts(int page) {
    Pageable pageable   = PageRequest.of(page,10);
        Page<Post> posts = postRepository.findAll(pageable);

        return posts.stream().map(post->new PostResponseDto(
                post.getId(),
                post.getUser().getUsername(),
                post.getTitle(),
                post.getContent(),
                post.getCountComments(),
                post.getCountLikes()
        )).collect(Collectors.toList());

    }

    @Override
    public void deletePost(Long id) {
     Post post = postRepository.findById(id)
             .orElseThrow(()-> new RuntimeException("없는 게시물입니다."));
      postRepository.delete(post);
    }
}
