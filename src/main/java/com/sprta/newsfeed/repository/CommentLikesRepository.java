package com.sprta.newsfeed.repository;

import com.sprta.newsfeed.entity.CommentLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentLikesRepository extends JpaRepository<CommentLikes, Long> {

    Optional<CommentLikes> findByUserIdAndCommentId(Long userId, Long commentId);

    boolean existsByUserIdAndCommentId(Long userId, Long commentId);

}
