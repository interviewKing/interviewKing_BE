package com.sparta.interview.repository;

import com.sparta.interview.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    //post를 userid로 찾기
    //user은 UserDetailsImpl로 받아올거임
    List<Post> findAllByLoginId(String userId);
    List<Post> findAllByOrderByDateDesc();
    List<Post> findAllByStack(String stack);

}