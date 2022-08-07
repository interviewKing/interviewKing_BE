package com.sparta.interview.repository;


import com.sparta.interview.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginId(String loginId); //아이디로 user 찾기
}
