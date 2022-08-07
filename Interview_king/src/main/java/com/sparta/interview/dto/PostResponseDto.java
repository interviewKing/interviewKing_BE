package com.sparta.interview.dto;

import com.sparta.interview.model.Post;
import lombok.Getter;

import java.util.List;

@Getter
public class PostResponseDto {
    private String loginId;
    private Long postId;
    private String username;
    private String title;
    private String content;
    private String companyname;
    private String stack;
    private String date;
    private List<CommentResponseDto> comments;

    public PostResponseDto(Post post, List<CommentResponseDto> commentResponseDtoList) {
        this.loginId = post.getLoginId();
        this.postId = post.getId();
        this.username = post.getUsername();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.companyname = post.getCompanyname();
        this.stack = post.getStack();
        this.date = post.getDate();
        this.comments = commentResponseDtoList;
    }
}
