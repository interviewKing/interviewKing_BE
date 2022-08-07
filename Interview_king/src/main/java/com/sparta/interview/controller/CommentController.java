package com.sparta.interview.controller;


import com.sparta.interview.dto.CommentRequestDto;
import com.sparta.interview.model.Comment;
import com.sparta.interview.security.UserDetailsImpl;
import com.sparta.interview.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController{
    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/api/comment/{postId}")
    public Comment createComment(@PathVariable Long postId, @RequestBody CommentRequestDto requestDto,
                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(postId, requestDto, userDetails);
    }

    // 댓글 수정
    @PutMapping("/api/comment/{id}")
    public Long updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto,
                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.updateComment(id, requestDto, userDetails);
    }

    // 댓글 삭제
    @DeleteMapping("/api/comment/{id}")
    public Long deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.deleteComment(id, userDetails);
    }
}