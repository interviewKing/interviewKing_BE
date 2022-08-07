package com.sparta.interview.security.jwt;

import com.sparta.interview.dto.LoginRequestDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtPreProcessingToken extends UsernamePasswordAuthenticationToken {

    private JwtPreProcessingToken(
            Object principal,
            Object credentials
    ) {
        super(
                principal,
                credentials

        );
    }

    public JwtPreProcessingToken(LoginRequestDto requestdto){
        this(requestdto.getLoginId(), requestdto.getPw());
    }

    public String getLoginId(){
        return (String) super.getPrincipal();
    }

    public String getPw(){
        return (String) super.getCredentials();
    }
    public JwtPreProcessingToken(String token) {
        this(
                token,
                token.length()
        );
    }
}