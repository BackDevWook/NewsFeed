package com.sprta.newsfeed.controller;

import com.sprta.newsfeed.repository.PostLikesRepository;
import com.sprta.newsfeed.service.PostLikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostLikesController {

    private final PostLikesRepository postLikesRepository;
    private final PostLikesService postLikesService;

    // 게시글 좋아요(URL : /api/posts/?/like?userId=?)
    @PostMapping("{postId}/like")
    public ResponseEntity<String> likePost(
            @PathVariable Long postId, // 좋아요할 게시글 ID
            @RequestParam Long userId  // 좋아요 누를 사용자 ID
    ) {
        postLikesService.likePost(userId, postId);
        return ResponseEntity.ok("게시글 좋아요 완료");
    }

    // 게시글 좋아요 취소 (URL :/api/posts/?/like?userId=?)
    @DeleteMapping("/{postId}/like")
    public ResponseEntity<String> unlikePost(
            @PathVariable Long postId,  // 좋아요 취소할 게시글 ID
            @RequestParam Long userId   // 좋아요 누를 사용자 ID
    ){
        postLikesService.unlikePost(userId, postId);
        return ResponseEntity.ok("게시글 좋아요 취소 완료");
    }

    // 게시글 좋아요 수 조회(URL : /api/posts/1/likes/count)
    @GetMapping("/{postId}/like/count")
    public ResponseEntity<Long> getLikeCount(@PathVariable Long postId) {
        Long likeCount = postLikesService.getLikeCount(postId);  // 좋아요 수를 조회할 게시글 ID
        return ResponseEntity.ok(likeCount);
    }

    //좋아요 수 많은 게시글 부터 정렬 (URL : /api/posts/likes/top)
    @GetMapping("/like/top")
    public ResponseEntity<?> getTopLikedPosts(@RequestParam(defaultValue = "10") int limit) {  // limit = 가져올 게시글 개수
        return ResponseEntity.ok(postLikesService.getTopLikedPosts(limit));
    }
}
