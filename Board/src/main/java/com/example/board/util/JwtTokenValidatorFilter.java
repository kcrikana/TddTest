package com.example.board.util;


import com.example.board.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("jwtTokenValidatorFilter")
@RequiredArgsConstructor
public class JwtTokenValidatorFilter {

	private final LoginService loginService;

}
