package com.example.board.service;

import com.example.board.domain.Member;
import com.example.board.dto.ReissueResult;
import com.example.board.exception.UnauthorizedException;
import com.example.board.repository.MemberRepository;
import com.example.board.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final MemberRepository memberRepository;
	private final JwtProvider jwtProvider;

	@Transactional
	public ReissueResult reissueTokens(String refreshToken) {
		if (refreshToken == null || refreshToken.isBlank()) {
			throw new UnauthorizedException("Refresh token not found");
		}

		if (!jwtProvider.validate(refreshToken)) {
			throw new UnauthorizedException("Invalid refresh token");
		}

		Member member = memberRepository.findByRefreshToken(refreshToken)
			.orElseThrow(() -> new UnauthorizedException("Refresh token not matched"));

		// 회전(rotate)
		String newAccess = jwtProvider.createAccessToken(member.getId(), member.getRole());
		String newRefresh = jwtProvider.createRefreshToken(member.getId());

		member.updateRefreshToken(newRefresh);
		memberRepository.save(member);

		return new ReissueResult(newAccess, newRefresh);
	}
}
