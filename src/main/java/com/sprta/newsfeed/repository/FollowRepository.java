package com.sprta.newsfeed.repository;

import com.sprta.newsfeed.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
}
