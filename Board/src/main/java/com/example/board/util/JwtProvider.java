package com.example.board.util;

import com.example.board.domain.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Component
@RequiredArgsConstructor
public class JwtProvider {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.access-exp-seconds}")
	private long accessExpSeconds;

	@Value("${jwt.refresh-exp-seconds}")
	private long refreshExpSeconds;

	private Key key() {
		return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	}

	public String createAccessToken(Long memberId, Role role) {
		Instant now = Instant.now();
		return Jwts.builder()
			.setSubject(String.valueOf(memberId))
			.claim("role", role.name())
			.setIssuedAt(Date.from(now))
			.setExpiration(Date.from(now.plusSeconds(accessExpSeconds)))
			.signWith(key(), SignatureAlgorithm.HS256)
			.compact();
	}

	public String createRefreshToken(Long memberId) {
		Instant now = Instant.now();
		return Jwts.builder()
			.setSubject(String.valueOf(memberId))
			.setIssuedAt(Date.from(now))
			.setExpiration(Date.from(now.plusSeconds(refreshExpSeconds)))
			.signWith(key(), SignatureAlgorithm.HS256)
			.compact();
	}

	public Long getMemberId(String token) {
		Claims claims = Jwts.parserBuilder()
			.setSigningKey(key())
			.build()
			.parseClaimsJws(token)
			.getBody();
		return Long.parseLong(claims.getSubject());
	}

	public Role getRole(String token) {
		Claims claims = Jwts.parserBuilder()
			.setSigningKey(key())
			.build()
			.parseClaimsJws(token)
			.getBody();
		String roleName = String.valueOf(claims.get("role"));
		return Role.valueOf(roleName);
	}

	public boolean validate(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}
}

