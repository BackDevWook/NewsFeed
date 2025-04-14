package com.sprta.newsfeed.service;

import com.sprta.newsfeed.entity.CommentLikes;

public interface CommentLikesService {

    // 1. 댓글 좋아요 누르기
    public void saveLike(Long userId, Long postId, Long commentId);
    // 2. 댓글 좋아요 취소
    public void deleteLike(Long userId, Long postId, Long commentId);
}
