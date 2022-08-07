package com.sparta.interview.controller;


import com.sparta.interview.dto.LoginRequestDto;
import com.sparta.interview.dto.SignupDto;
import com.sparta.interview.model.User;
import com.sparta.interview.security.UserDetailsImpl;
import com.sparta.interview.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    //로그인
    @PostMapping("/api/login")
    public ResponseEntity login(@RequestBody LoginRequestDto loginRequestDto,HttpServletRequest servletRequest) {
        User user = userService.login(loginRequestDto);
        HttpSession session=servletRequest.getSession();
        session.setAttribute("user",user);
        return new ResponseEntity(user,HttpStatus.OK);

    }

    // 회원 가입 요청 처리
    @PostMapping("/api/signup")
    public void registerUser(@Valid @RequestBody SignupDto requestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            for (ObjectError error : errorList)
                System.out.println(error.getDefaultMessage());
        }
        userService.registerUser(requestDto);
        System.out.println("회원가입 성공!");

    }

    @GetMapping("/api/user")
    public ResponseEntity<?> getUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return new ResponseEntity<>(userService.getUser(userDetails), HttpStatus.valueOf(200));
    }

    @GetMapping("/api/mypage")
    public ResponseEntity<?> getMyPost(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return new ResponseEntity<>(userService.getMyPost(userDetails), HttpStatus.OK);
    }
}