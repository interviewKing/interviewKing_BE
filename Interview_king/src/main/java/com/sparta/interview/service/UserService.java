package com.sparta.interview.service;


import com.sparta.interview.dto.LoginRequestDto;
import com.sparta.interview.dto.MyPostResponseDto;
import com.sparta.interview.dto.SignupDto;
import com.sparta.interview.dto.UserResponseDto;
import com.sparta.interview.model.Post;
import com.sparta.interview.model.User;
import com.sparta.interview.repository.PostRepository;
import com.sparta.interview.repository.UserRepository;
import com.sparta.interview.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;


@Service
@RequiredArgsConstructor
public class UserService  {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    public User login(LoginRequestDto loginRequestDto){

        User user = userRepository.findByLoginId(loginRequestDto.getLoginId())
                .orElseThrow(() -> new NullPointerException("아이디가 일치하지 않습니다"));
        if (user==null||!passwordEncoder.matches(loginRequestDto.getPw(),user.getPw())) {
            //requestdto가 원래 저장되어있는거, user이 받은거
            throw new NullPointerException("비밀번호가 일치하지 않습니다.");
        }
        else
            System.out.println("로그인 성공");

        SecurityContextHolder.getContext().setAuthentication(getAuthentication(user.getLoginId()));
        return user;
    }
    public UsernamePasswordAuthenticationToken getAuthentication(String loginId) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginId);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    //로그인 된 유저 정보 찾기
    public UserResponseDto getUser(UserDetailsImpl userDetails){

        /*Optional<User> user = Optional.ofNullable(userRepository.findByLoginId(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("ㅇㄴㄹㄴㅇㄹㄴ")));
        return new UserResponseDto(userDetails.getUser());*/
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setUsername(userDetails.getUser().getUsername());
        userResponseDto.setLoginId(userDetails.getUser().getLoginId());
        return userResponseDto;
    }



    @Transactional
    public User registerUser(SignupDto requestDto) {
        String error="";
        String loginId = requestDto.getLoginId();
        String password = requestDto.getPw();
        String passwordcheck = requestDto.getPwcheck();
        String username = requestDto.getUsername();
        String pattern = "^[a-zA-Z0-9`~!@#$%^&*()-_=+]{3,}$";//대소문자,숫자,특수문자


        Optional<User> found = userRepository.findByLoginId(loginId);
        if (found.isPresent()) {
            throw new NullPointerException("이미 존재하는 아이디입니다.");
        }

        if (!Pattern.matches(pattern, password))
            throw new NullPointerException("비밀번호는 대소문자와 숫자를 포함해주세요.");

        if (password.length()<4)
            throw new NullPointerException("비밀번호는 4자이상 입력해주세요.");

        if (!Objects.equals(password,passwordcheck))
            throw new NullPointerException("비밀번호가 일치하지 않습니다.");

        password = passwordEncoder.encode(requestDto.getPw());//비밀번호 암호화
        requestDto.setPw(password);//암호화된 비밀번호 set
        User user = new User(loginId,password,username);
        userRepository.save(user);
        return user;

    }

    public List<MyPostResponseDto> getMyPost(UserDetailsImpl userDetails) {
        List<Post> posts = postRepository.findAllByLoginId(userDetails.getUsername());
        List<MyPostResponseDto> myPostResponseDtoList = new ArrayList<>();
        for (Post post : posts) {
            MyPostResponseDto myPostResponseDto = new MyPostResponseDto(post);
            myPostResponseDtoList.add(myPostResponseDto);
        }
        return myPostResponseDtoList;
    }
}