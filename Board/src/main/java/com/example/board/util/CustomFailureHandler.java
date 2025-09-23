package com.example.board.util;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomFailureHandler implements AuthenticationFailureHandler {


	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception) throws IOException, ServletException {

		String errorMessage;
		if (exception instanceof BadCredentialsException) {
			errorMessage = "아이디 또는 비밀번호가 맞지 않습니다.";
		} else if (exception instanceof InternalAuthenticationServiceException) {
			errorMessage = "내부 시스템 문제로 로그인 요청을 처리할 수 없습니다.";
		} else if (exception instanceof UsernameNotFoundException) {
			errorMessage = "존재하지 않는 계정입니다.";
		} else if (exception instanceof AuthenticationCredentialsNotFoundException) {
			errorMessage = "인증 요청이 거부되었습니다.";
		} else {
			errorMessage = "알 수 없는 오류로 로그인에 실패했습니다.";
		}

		request.getSession().setAttribute("loginErrorMsg", errorMessage);
		response.sendRedirect("/members/login");
	}
}
