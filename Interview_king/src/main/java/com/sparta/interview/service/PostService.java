package com.sparta.interview.service;


import com.sparta.interview.dto.CommentResponseDto;
import com.sparta.interview.dto.PostRequestDto;
import com.sparta.interview.dto.PostResponseDto;
import com.sparta.interview.model.Comment;
import com.sparta.interview.model.Post;
import com.sparta.interview.model.User;
import com.sparta.interview.repository.CommentRepository;
import com.sparta.interview.repository.PostRepository;
import com.sparta.interview.repository.UserRepository;
import com.sparta.interview.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;


    public List<Post> getPosts(String userId) {
        return postRepository.findAllByLoginId(userId);
    }

    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("게시글 없다"));
        List<Comment> comments = commentRepository.findAllByPostIdOrderByDateDesc(id);
        List<CommentResponseDto> responseDtoList = new ArrayList<>();
        for(Comment comment : comments) {
            CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
            responseDtoList.add(commentResponseDto);
        }
        return new PostResponseDto(post, responseDtoList);
    }

    // 글쓰기
    @Transactional
    public Post createPost(PostRequestDto requestDto, String loginId) {
        User user = userRepository.findByLoginId(loginId).orElseThrow(() -> new NullPointerException("사용자가 존재하지 않습니다"));

        Post post = new Post(requestDto, user);
        post = postRepository.save(post);
        return post;
    }

    // 업데이트
    public void editPost(Long postId, UserDetailsImpl userDetails, PostRequestDto requestDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 포스트입니다."));
        if(!userDetails.getUser().getId().equals(post.getUser().getId())){
            throw new IllegalArgumentException("접근 권한이 없는 사용자입니다.");
        }
        post.updatePost(requestDto);
        postRepository.save(post);
    }


    // 삭제
    public void deletePost(Long postId, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 포스트입니다."));
        if(!userDetails.getUser().getId().equals(post.getUser().getId())){
            throw new IllegalArgumentException("접근 권한이 없는 사용자입니다.");
        }
        postRepository.deleteById(postId);
    }




    // 카테고리
    @Transactional
    public List<Post> getPostByStack(String stack) {
        if(stack.equals("All"))
            return postRepository.findAll();
        else
            return postRepository.findAllByStack(stack);

    }
}