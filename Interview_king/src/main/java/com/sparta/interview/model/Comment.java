package com.sparta.interview.model;


import com.sparta.interview.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

//@Table(name = "comments")
@Getter @Setter
@NoArgsConstructor
@Entity
public class Comment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String content;

    private String date;

    @Column(nullable = false)
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Comment(CommentRequestDto requestDto, Long postid) {
        this.content = requestDto.getComment();
    }

    public boolean isCommenter(User user) {
        return this.user.equals(user);
    }


    public void update(CommentRequestDto requestDto) {
        this.content = requestDto.getComment();
        this.date = requestDto.getDate();
    }


    public Comment(String content,User user,Long postId) {
        this.postId = postId;
        this.content = content;
        this.user = user;
    }
    public Comment(CommentRequestDto requestDto,User user,Long postId) {
        this.postId = postId;
        this.content = requestDto.getComment();
        this.date = requestDto.getDate();
        this.user = user;
    }


}
