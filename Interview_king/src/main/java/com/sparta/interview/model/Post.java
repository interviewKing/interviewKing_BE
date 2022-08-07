package com.sparta.interview.model;


import com.sparta.interview.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//@Table(name = "posts")

@Getter
@NoArgsConstructor
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    //제목
    @Column(nullable = false)
    private String title;

    // 회사이름
    @Column(nullable = false)
    private String companyname;

    // 기술
    @Column(nullable = false)
    private String stack;

    // 게시글 내용
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String date;


    @Column(nullable = false)
    private String loginId; //글쓴사람 아이디

    @Column(nullable = false)
    private String username; //글쓴사람 이름

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public Post(PostRequestDto requestDto, User user) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.stack = requestDto.getStack();
        this.companyname = requestDto.getCompanyname();
        this.loginId = user.getLoginId();
        this.username = user.getUsername();
        this.user = user;
        this.date = requestDto.getDate();
    }

    public void updatePost(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.stack = requestDto.getStack();
        this.companyname = requestDto.getCompanyname();
        this.date = requestDto.getDate();
    }

    public void addComment(Comment comment) {
        comment.isCommenter(user);
    }


}
