package com.example.board.controller;

import com.example.board.dto.ReissueResult;
import com.example.board.dto.ResponseMemberDto;
import com.example.board.service.AuthService;
import com.example.board.service.MemberService;
import com.example.board.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;
	private final CookieUtil cookieUtil;
	private final MemberService memberService;

	@Value("${jwt.refresh-cookie-name:refreshToken}")
	private String refreshCookieName;

	@Value("${jwt.refresh-exp-seconds}")
	private long refreshExpSeconds;

	@Value("${jwt.cookie-secure:false}")
	private boolean cookieSecure;

	@PostMapping("/refresh")
	public ResponseEntity<TokenResponse> refresh(HttpServletRequest request, HttpServletResponse response) {

		// 1) 쿠키에서 refreshToken 추출
		String refreshToken = extractCookie(request, refreshCookieName);

		// 2) 서비스에서 검증/회전/저장까지 처리 후 새 토큰 받기
		ReissueResult result = authService.reissueTokens(refreshToken);

		// 3) 새 refreshToken을 쿠키로 세팅
		response.addHeader(
			HttpHeaders.SET_COOKIE,
			cookieUtil.refreshCookie(refreshCookieName, result.refreshToken(), refreshExpSeconds, cookieSecure).toString()
		);

		// 4) accessToken은 바디로 반환
		return ResponseEntity.ok(new TokenResponse(result.accessToken()));
	}

	@GetMapping("/me")
	public ResponseEntity<ResponseMemberDto> findByInfo(Authentication authentication) {

		Long memberId = (Long) authentication.getPrincipal();
		return ResponseEntity.ok(memberService.findMemberById(memberId));
	}

	private String extractCookie(HttpServletRequest request, String name) {
		if (request.getCookies() == null) return null;
		return Arrays.stream(request.getCookies())
			.filter(c -> c.getName().equals(name))
			.map(c -> c.getValue())
			.findFirst()
			.orElse(null);
	}

	public record TokenResponse(String accessToken) {}
}
