package com.example.board.util;

import com.example.board.domain.Member;
import com.example.board.repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

	private final MemberRepository memberRepository;
	private final CookieUtil cookieUtil;

	@Value("${jwt.refresh-cookie-name:refreshToken}")
	private String refreshCookieName;

	@Value("${jwt.cookie-secure:false}")
	private boolean cookieSecure;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		// 1) 쿠키에서 refreshToken 꺼내기
		String refreshToken = extractCookie(request, refreshCookieName);

		// 2) DB에 저장된 refreshToken 제거 (재발급 차단)
		if (refreshToken != null && !refreshToken.isBlank()) {
			memberRepository.findByRefreshToken(refreshToken).ifPresent(member -> {
				member.clearRefreshToken();
				memberRepository.save(member);
			});
		}

		// 3) 브라우저 쿠키 삭제
		response.addHeader(
			HttpHeaders.SET_COOKIE,
			cookieUtil.deleteCookie(refreshCookieName, cookieSecure).toString()
		);
	}

	private String extractCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) return null;

		return Arrays.stream(cookies)
			.filter(c -> name.equals(c.getName()))
			.map(Cookie::getValue)
			.findFirst()
			.orElse(null);
	}
}
