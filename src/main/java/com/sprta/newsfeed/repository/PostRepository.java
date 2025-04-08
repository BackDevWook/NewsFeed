package com.sprta.newsfeed.repository;

import com.sprta.newsfeed.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
