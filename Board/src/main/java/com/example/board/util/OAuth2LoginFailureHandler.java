package com.example.board.util;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2LoginFailureHandler implements AuthenticationFailureHandler {

	private final CookieUtil cookieUtil;

	@Value("${KAKAO_SUCCESS_REDIRECT_URI}")
	private String redirectUri;

	@Value("${jwt.refresh-cookie-name:refreshToken}")
	private String refreshCookieName;

	@Value("${jwt.cookie-secure:false}")
	private boolean cookieSecure;

	@Override
	public void onAuthenticationFailure(
		HttpServletRequest request,
		HttpServletResponse response,
		AuthenticationException exception
	) throws IOException, ServletException {

		// 1) 혹시 남아있는 refresh 쿠키 삭제
		response.addHeader(
			HttpHeaders.SET_COOKIE,
			cookieUtil.deleteCookie(refreshCookieName, cookieSecure).toString()
		);

		// 2) 실패 이유 전달(최소한의 정보만)
		String message = exception.getMessage() == null ? "OAuth2 login failed" : exception.getMessage();
		String encoded = URLEncoder.encode(message, StandardCharsets.UTF_8);

		response.sendRedirect(redirectUri + "?error=oauth2_failed&message=" + encoded);
	}
}
