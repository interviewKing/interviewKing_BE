package com.sparta.interview.repository;

import com.sparta.interview.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPostIdOrderByDateDesc(Long postId);

}
