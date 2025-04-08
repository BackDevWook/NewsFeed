package com.sprta.newsfeed.repository;

import com.sprta.newsfeed.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public class CommentRepository extends JpaRepository<Comment, Long> {
}
