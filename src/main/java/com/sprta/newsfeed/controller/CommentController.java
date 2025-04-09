package com.sprta.newsfeed.controller;

import com.sprta.newsfeed.dto.Comment.CommentResponseDto;
import com.sprta.newsfeed.dto.Comment.CommentWithUsernameResponseDto;
import com.sprta.newsfeed.dto.Comment.CreateCommentRequestDto;
import com.sprta.newsfeed.dto.Comment.UpdateCommentRequestDto;
import com.sprta.newsfeed.exception.comment.ErrorResponse;
import com.sprta.newsfeed.exception.comment.NotFoundException;
import com.sprta.newsfeed.service.CommentService;
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


    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody CreateCommentRequestDto requestDto) {

        CommentResponseDto commentResponseDto = commentService.save(requestDto.getContent());

        return new ResponseEntity<>(commentResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> findAll() {

        List<CommentResponseDto> commentResponseDtoList = commentService.findAll();

        return new ResponseEntity<>(commentResponseDtoList, HttpStatus.OK);
    }

    @PatchMapping("{commentId}")
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
