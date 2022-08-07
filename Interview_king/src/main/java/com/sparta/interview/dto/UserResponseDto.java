package com.sparta.interview.dto;

import com.sparta.interview.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {
    private String loginId;
    private String username;

    public UserResponseDto(User user) {
        this.loginId=user.getLoginId();
        this.username=user.getUsername();
    }

}
