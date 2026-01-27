package com.example.board.controller;


import com.example.board.domain.Member;
import com.example.board.repository.MemberRepository;
import com.example.board.util.CookieUtil;
import com.example.board.util.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

	private final MemberRepository memberRepository;
	private final JwtProvider jwtProvider;
	private final CookieUtil cookieUtil;

	@Value("${jwt.refresh-cookie-name:refreshToken}")
	private String refreshCookieName;

	@Value("${jwt.refresh-exp-seconds}")
	private long refreshExpSeconds;

	@Value("${jwt.cookie-secure:false}")
	private boolean cookieSecure;

	@PostMapping("/refresh")
	public ResponseEntity<?> refresh(HttpServletRequest request, HttpServletResponse response) {

		String refreshToken = extractCookie(request, refreshCookieName);
		if (refreshToken == null) {
			return ResponseEntity.status(401).body("Refresh token not found");
		}

		if (!jwtProvider.validate(refreshToken)) {
			return ResponseEntity.status(401).body("Invalid refresh token");
		}

		// DB에 저장된 refreshToken과 일치하는지 확인 (탈취/재사용 방어)
		Member member = memberRepository.findByRefreshToken(refreshToken)
			.orElse(null);

		if (member == null) {
			return ResponseEntity.status(401).body("Refresh token not matched");
		}

		// 새 토큰 발급(회전)
		String newAccess = jwtProvider.createAccessToken(member.getId(), member.getRole());
		String newRefresh = jwtProvider.createRefreshToken(member.getId());

		member.updateRefreshToken(newRefresh);
		memberRepository.save(member);

		// 새 refresh 쿠키 세팅
		response.addHeader(HttpHeaders.SET_COOKIE,
			cookieUtil.refreshCookie(refreshCookieName, newRefresh, refreshExpSeconds, cookieSecure).toString()
		);

		// accessToken은 바디로 반환(프론트가 Authorization 헤더로 넣게)
		return ResponseEntity.ok(new TokenResponse(newAccess));
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
