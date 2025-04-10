package com.sprta.newsfeed.service;


import com.sprta.newsfeed.dto.PostCreateRequestDto;
import com.sprta.newsfeed.dto.PostResponseDto;
import com.sprta.newsfeed.dto.PostUpdateRequestDto;
import com.sprta.newsfeed.entity.Comment;
import com.sprta.newsfeed.entity.Post;
import com.sprta.newsfeed.entity.User;
import com.sprta.newsfeed.repository.CommentRepository;
import com.sprta.newsfeed.repository.PostRepository;
import com.sprta.newsfeed.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor //final로 선언된 필드에 대해 생성자 자동생성
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Override
    //게시글 작성 로직
    public PostResponseDto createPost(PostCreateRequestDto requestDto, Long loginUserId) {
        User user = userRepository.findById(loginUserId)
                .orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다."));

        Post post = new Post(requestDto.getTitle(), requestDto.getContent(), user);
        Post saved = postRepository.save(post);

        return new PostResponseDto(
                saved.getId(),
                saved.getUser().getUserName(),
                saved.getTitle(),
                saved.getContent(),
                saved.getCountComments(),
                saved.getLikesCount()
        );
    }

    @Override
    @Transactional
    //게시글 수정 로직
    public PostResponseDto updatePost(Long id, PostUpdateRequestDto requestDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("없는 게시물입니다."));
        post.updateContent(requestDto.getContent());

        return new PostResponseDto(
                post.getId(),
                post.getUser().getUserName(),
                post.getTitle(),
                post.getContent(),
                post.getCountComments(),
                post.getLikesCount()
        );
    }

    @Override
    //게시글 조회 로직
    public List<PostResponseDto> getAllPosts(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Post> posts = postRepository.findAll(pageable);

        return posts.stream().map(post -> new PostResponseDto(
                post.getId(),
                post.getUser().getUserName(),
                post.getTitle(),
                post.getContent(),
                post.getCountComments(),
                post.getLikesCount()
        )).collect(Collectors.toList());

    }

    @Override
    // 게시글 + 댓글 조회
    public PostResponseDto getPostWithComments(Long id) {
      Post post = postRepository.findById(id)
              .orElseThrow(()-> new RuntimeException("없는 게시물입니다."));

      List<Comment> comments = commentRepository.findAllByPostId(id);

      return new PostResponseDto(post, comments);
    }


    @Override
    //게시글 삭제 로직
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("없는 게시물입니다."));
        postRepository.delete(post);
    }
}
