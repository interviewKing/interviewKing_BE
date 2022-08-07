package com.sparta.interview.dto;

import com.sparta.interview.model.Post;
import lombok.Getter;

@Getter
public class MyPostResponseDto {
    private Long postId;
    private String stack;
    private String title;
    private String content;
    private String companyname;
    private String date;

    public MyPostResponseDto(Post post) {
        this.postId = post.getId();
        this.stack = post.getStack();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.companyname = post.getCompanyname();
        this.date = post.getDate();
    }
}
