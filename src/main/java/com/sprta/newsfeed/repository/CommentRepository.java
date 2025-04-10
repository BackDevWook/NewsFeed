package com.sprta.newsfeed.repository;

import com.sprta.newsfeed.entity.Comment;
import com.sprta.newsfeed.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPostId(Long id);

    List<Comment> findByPost(Post post);
}
