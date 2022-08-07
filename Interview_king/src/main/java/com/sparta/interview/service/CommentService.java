package com.sparta.interview.service;


import com.sparta.interview.dto.CommentRequestDto;
import com.sparta.interview.model.Comment;
import com.sparta.interview.repository.CommentRepository;
import com.sparta.interview.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    // 댓글 작성
    @Transactional
    public Comment createComment(Long postId, CommentRequestDto requestDto, UserDetailsImpl userDetails) {
        String replyCheck = requestDto.getComment();
        if (replyCheck.contains("script") || replyCheck.contains("<") || replyCheck.contains(">")) {
            Comment comment = new Comment("잘못된 입력입니다(XSS 공격금지)", userDetails.getUser() ,postId);
            commentRepository.save(comment);
            return comment;
        }
        Comment comment = new Comment(requestDto, userDetails.getUser(), postId);
        commentRepository.save(comment);
        return comment;
    }

    // 댓글 수정
    @Transactional
    public Long updateComment(Long id, CommentRequestDto requestDto, UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다"));
        if(!userDetails.getUser().getId().equals(comment.getUser().getId())){
            throw new IllegalArgumentException("접근 권한이 없는 사용자입니다.");
        }
        comment.update(requestDto);
        commentRepository.save(comment);
        return id;
    }


    public Long deleteComment(Long id, UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다")
        );
        if(!userDetails.getUser().getId().equals(comment.getUser().getId())){
            throw new IllegalArgumentException("접근 권한이 없는 사용자입니다.");
        }
        commentRepository.deleteById(id);
        return id;
    }
}