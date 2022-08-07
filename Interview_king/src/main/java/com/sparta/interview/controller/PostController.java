package com.sparta.interview.controller;

import com.sparta.interview.dto.PostRequestDto;
import com.sparta.interview.dto.PostResponseDto;
import com.sparta.interview.model.Post;
import com.sparta.interview.repository.PostRepository;
import com.sparta.interview.security.UserDetailsImpl;
import com.sparta.interview.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController{
    private final PostRepository postRepository;
    private final PostService postService;


    // Post 작성
    @PostMapping("/api/post")
    public Post createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        String loginId = userDetails.getUser().getLoginId();
        Post post = postService.createPost(requestDto,loginId);
        return post;
    }


    // Post 수정
    @PutMapping("/api/post/{postId}")
    public void editPost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto,
                         @AuthenticationPrincipal UserDetailsImpl userDetails){
        postService.editPost(postId, userDetails, requestDto);
    }

    // Post 삭제
    @DeleteMapping("/api/post/{postId}")
    public void deletePost(@PathVariable Long postId,
                           @AuthenticationPrincipal UserDetailsImpl userDetails){
        postService.deletePost(postId, userDetails);
    }


    // Post 게시글 조회
    @GetMapping("/api/posts")
    public List<Post> getPosts() {
        return postRepository.findAllByOrderByDateDesc();
    }


    // Post 게시글 디테일 조회
    @GetMapping("/api/posts/{postId}")
    public PostResponseDto getPost(@PathVariable Long postId) {
        return postService.getPost(postId);
    }


    // Post 카테고리 검색
    @GetMapping("/api/{stack}")
    public List<Post> getPostByStack(@PathVariable String stack) {
        return postService.getPostByStack(stack);
    }
}