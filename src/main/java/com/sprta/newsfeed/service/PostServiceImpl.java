package com.sprta.newsfeed.service;


import com.sprta.newsfeed.dto.Post.PostCreateRequestDto;
import com.sprta.newsfeed.dto.Post.PostResponseDto;
import com.sprta.newsfeed.dto.Post.PostUpdateRequestDto;
import com.sprta.newsfeed.entity.Comment;
import com.sprta.newsfeed.entity.Post;
import com.sprta.newsfeed.entity.User;
import com.sprta.newsfeed.repository.CommentRepository;
import com.sprta.newsfeed.repository.PostLikesRepository;
import com.sprta.newsfeed.repository.PostRepository;
import com.sprta.newsfeed.repository.UserRepository;
import com.sprta.newsfeed.security.customerror.CustomException;
import com.sprta.newsfeed.security.customerror.ErrorCode;
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
    private final PostLikesRepository postLikesRepository;

    @Override
    //게시글 작성 로직
    public PostResponseDto createPost(PostCreateRequestDto requestDto, Long loginUserId) {
        User user = userRepository.findById(loginUserId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Post post = new Post(requestDto.getTitle(), requestDto.getContent(), user);
        Post saved = postRepository.save(post);

        return new PostResponseDto(
                saved.getId(),
                saved.getUser().getUserName(),
                saved.getTitle(),
                saved.getContent(),
                saved.getCountComments(),
                postLikesRepository.countByPost(post)
        );
    }

    @Override
    @Transactional
    //게시글 수정 로직
    public PostResponseDto updatePost(Long id, PostUpdateRequestDto requestDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        post.updateContent(requestDto.getContent());

        return new PostResponseDto(
                post.getId(),
                post.getUser().getUserName(),
                post.getTitle(),
                post.getContent(),
                post.getCountComments(),
                postLikesRepository.countByPost(post)
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
                postLikesRepository.countByPost(post)
        )).collect(Collectors.toList());

    }

    @Override
    // 게시글 + 댓글 조회
    public PostResponseDto getPostWithComments(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        List<Comment> comments = commentRepository.findAllByPostId(id);
        Long likeCount = postLikesRepository.countByPost(post); // 좋아요 수

        return new PostResponseDto(post, comments, likeCount);
    }


    @Override
    //게시글 삭제 로직
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        postRepository.delete(post);
    }

    public Post findById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));  // 게시물이 없으면 예외 던짐
    }
}
