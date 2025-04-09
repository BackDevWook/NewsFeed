package com.sprta.newsfeed.controller;

import com.sprta.newsfeed.common.Const;
import com.sprta.newsfeed.dto.Comment.CommentResponseDto;
import com.sprta.newsfeed.dto.Comment.CommentWithUsernameResponseDto;
import com.sprta.newsfeed.dto.Comment.CreateCommentRequestDto;
import com.sprta.newsfeed.dto.Comment.UpdateCommentRequestDto;
import com.sprta.newsfeed.dto.LoginResponseDto;
import com.sprta.newsfeed.entity.User;
import com.sprta.newsfeed.exception.comment.ErrorResponse;
import com.sprta.newsfeed.exception.comment.NotFoundException;
import com.sprta.newsfeed.repository.UserRepository;
import com.sprta.newsfeed.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final UserRepository userRepository;


    @PostMapping
    public ResponseEntity<?> save(
            @Valid @RequestBody CreateCommentRequestDto requestDto,
            HttpServletRequest request
    ) {
        // 로그인된 사용자 정보를 세션에서 가져옴
        LoginResponseDto loginUser = (LoginResponseDto) request.getSession().getAttribute(Const.LOGIN_USER);

        // 로그인되지 않은 경우 UNAUTHORIZED(401) 상태 코드 반환
        if (loginUser == null) {
            return new ResponseEntity<>("로그인이 필요합니다", HttpStatus.UNAUTHORIZED);
        }

        // 세션에서 가져온 사용자 정보를 기반으로 DB에서 사용자 조회
        User user = userRepository.findById(loginUser.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다"));

        // 댓글 작성 서비스 호출: 사용자가 작성한 내용을 DB에 저장하고, 반환된 DTO를 클라이언트에게 전달
        CommentResponseDto commentResponseDto = commentService.save(user, requestDto.getContent());

        // 댓글 저장 성공 후, 생성된 댓글 정보와 HTTP 상태 코드 CREATED(201) 반환
        return new ResponseEntity<>(commentResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> findAll() {

        List<CommentResponseDto> commentResponseDtoList = commentService.findAll();

        return new ResponseEntity<>(commentResponseDtoList, HttpStatus.OK);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(
            @PathVariable Long commentId,
            @RequestBody @Valid UpdateCommentRequestDto requestDto
            ) {
        commentService.updateComment(commentId, requestDto.getNewContent());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(@PathVariable Long commentId) {
        commentService.delete(commentId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 댓글 404 에러
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse("404", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


}
