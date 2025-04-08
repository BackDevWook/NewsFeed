package com.sprta.newsfeed.controller;

import com.sprta.newsfeed.dto.Comment.CommentResponseDto;
import com.sprta.newsfeed.dto.Comment.CommentWithUsernameResponseDto;
import com.sprta.newsfeed.dto.Comment.CreateCommentRequestDto;
import com.sprta.newsfeed.dto.Comment.UpdateCommentRequestDto;
import com.sprta.newsfeed.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{id}/comments")
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

//    @PatchMapping("{id}")
//    public ResponseEntity<Void> updateComment(
//            @PathVariable Long id,
//            @RequestBody @Valid UpdateCommentRequestDto requestDto
//            ) {
//        commentService.updateComment(id, requestDto.getNewContent());
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }


}
