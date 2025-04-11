package com.sprta.newsfeed.controller;

import com.sprta.newsfeed.common.Const;
import com.sprta.newsfeed.dto.Comment.CommentResponseDto;
import com.sprta.newsfeed.dto.Comment.CreateCommentRequestDto;
import com.sprta.newsfeed.dto.Comment.UpdateCommentRequestDto;
import com.sprta.newsfeed.dto.Login.LoginResponseDto;
import com.sprta.newsfeed.entity.User;
import com.sprta.newsfeed.service.CommentService;
import com.sprta.newsfeed.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    /**
     * 댓글 작성
     * @param postId 해당 게시물 고유 식별자 ID
     * @param requestDto 댓글 작성 content
     * @param loginUser 사용자 정보를 세션에서 가져옴
     * @return 작성된 댓글과 성공하면 200 OK 반환
     */
    @PostMapping
    public ResponseEntity<?> save(
            @PathVariable Long postId,
            @Valid @RequestBody CreateCommentRequestDto requestDto,
            @SessionAttribute(name = Const.LOGIN_USER) LoginResponseDto loginUser
    ) {
        // 세션에서 가져온 사용자 정보로 사용자 조회
        User user = userService.findById(loginUser.getUserId());
        // 댓글 작성 서비스 호출 후 사용자가 작성한 내용을 저장
        CommentResponseDto commentResponseDto = commentService.save(postId, user, requestDto.getContent());
        return new ResponseEntity<>(commentResponseDto, HttpStatus.CREATED);
    }

    /**
     * 특정 게시물 댓글 조회
     * @param postId 해당 게시물 고유 식별자 ID값
     * @return 해당 게시물 댓글들과 200 OK 반환
     */
    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> findAll(
            @PathVariable Long postId
    ) {
        // 해당 게시물 ID값
        List<CommentResponseDto> commentResponseDtoList = commentService.findAllByPost(postId);
        return new ResponseEntity<>(commentResponseDtoList, HttpStatus.OK);
    }

    /**
     * 댓글 수정 (댓글 작성자만 수정 가능)
     * @param commentId 댓글 고유 식별자 ID값
     * @param requestDto 댓글 수정 request content
     * @param loginUser 세션 정보에서 로그인한 사용자 정보 가져오기
     * @return 모든 조건을 통과하면 댓글 수정이 수정되고 OK 반환
     */
    @PatchMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(
            @PathVariable Long commentId,
            @RequestBody @Valid UpdateCommentRequestDto requestDto,
            @SessionAttribute(name = Const.LOGIN_USER) LoginResponseDto loginUser
            ) {
        commentService.updateComment(commentId, requestDto.getNewContent(), loginUser.getUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 댓글 삭제 (해당 게시물 작성자 또는 댓글 작성자만 삭제 가능)
     * @param commentId 해당 게시물 고유 식별자 ID값
     * @param loginUser 사용자 정보를 세션에서 가져옴
     * @return
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long commentId,
            @SessionAttribute(name = Const.LOGIN_USER) LoginResponseDto loginUser
    ) {
        commentService.delete(commentId, loginUser.getUserId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
