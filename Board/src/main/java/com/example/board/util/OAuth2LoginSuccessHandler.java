package com.example.board.util;

import com.example.board.domain.Adress;
import com.example.board.domain.Member;
import com.example.board.domain.SocialType;
import com.example.board.repository.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

	private final MemberRepository memberRepository;
	private final JwtProvider jwtProvider;
	private final CookieUtil cookieUtil;

	@Value("${KAKAO_SUCCESS_REDIRECT_URI}")
	private String redirectUri;

	@Value("${jwt.refresh-cookie-name:refreshToken}")
	private String refreshCookieName;

	@Value("${jwt.refresh-exp-seconds}")
	private long refreshExpSeconds;

	@Value("${jwt.cookie-secure:false}")
	private boolean cookieSecure;

	@Override
	public void onAuthenticationSuccess(
		HttpServletRequest request,
		HttpServletResponse response,
		Authentication authentication
	) throws IOException, ServletException {

		// OAuth2User attributes에서 socialId 추출
		// authentication.getPrincipal()은 보통 OAuth2User
		Object principal = authentication.getPrincipal();
		String socialId = extractKakaoId(principal);

		// 회원 조회 or 생성
		Member member = memberRepository.findBySocialTypeAndSocialId(SocialType.KAKAO, socialId)
			.orElseGet(() -> memberRepository.save(
				Member.builder()
					.socialType(SocialType.KAKAO)
					.socialId(socialId)
					.name("member")      // TODO: 카카오 프로필에서 닉네임 있으면 세팅
					.password("OAUTH2")      // OAuth2라 의미 없는 값. (실무에선 null 처리/컬럼 분리 추천)
					.tel("000-0000-0000")    // TODO: 필요시 입력받기
					.adress(new Adress(null, null, null, null))
					.build()
			));

		// 토큰 발급
		String accessToken = jwtProvider.createAccessToken(member.getId(), member.getRole());
		String refreshToken = jwtProvider.createRefreshToken(member.getId());

		// refreshToken DB 저장(회전/로그아웃 대비)
		member.updateRefreshToken(refreshToken);
		memberRepository.save(member);

		// refreshToken 쿠키로 세팅(HttpOnly)
		response.addHeader(HttpHeaders.SET_COOKIE,
			cookieUtil.refreshCookie(refreshCookieName, refreshToken, refreshExpSeconds, cookieSecure).toString()
		);

		// accessToken 전달 방식: redirect query로 전달(프론트 나중에 손본다 했으니 가장 간단)
		// ex) http://localhost:5173/oauth2/success?accessToken=...
		response.sendRedirect(redirectUri + "?accessToken=" + accessToken);
	}

	@SuppressWarnings("unchecked")
	private String extractKakaoId(Object principal) {
		// 기본 구현: OAuth2User -> attributes(Map)에서 id
		// 런타임 캐스팅 실패할 수 있으니 방어적으로 처리
		try {
			// org.springframework.security.oauth2.core.user.OAuth2User
			Map<String, Object> attrs =
				(Map<String, Object>) principal.getClass().getMethod("getAttributes").invoke(principal);

			Object id = attrs.get("id");
			if (id != null) return String.valueOf(id);

			// 혹시 중첩 구조면 여기서 추가 파싱
			// Object account = attrs.get("kakao_account") ...
		} catch (Exception ignored) {}

		throw new IllegalStateException("카카오 socialId(id) 추출 실패: OAuth2 attributes 구조를 확인해야 합니다.");
	}
}
