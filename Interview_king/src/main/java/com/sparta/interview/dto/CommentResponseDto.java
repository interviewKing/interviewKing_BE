package com.sparta.interview.dto;

import com.sparta.interview.model.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private Long commentId;
    private String loginId;
    private String comment;
    private String date;
    private String username;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.loginId = comment.getUser().getLoginId();
        this.comment = comment.getContent();
        this.date = comment.getDate();
        this.username = comment.getUser().getUsername();
    }
}
