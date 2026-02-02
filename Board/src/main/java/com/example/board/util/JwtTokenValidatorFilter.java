package com.example.board.util;


import com.example.board.domain.Role;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Qualifier("jwtTokenValidatorFilter")
@RequiredArgsConstructor
public class JwtTokenValidatorFilter extends OncePerRequestFilter {

	private final JwtProvider jwtProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		String header = request.getHeader("Authorization");
		if(header == null || !header.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = header.substring(7);

		if(!jwtProvider.validate(token)) {
			filterChain.doFilter(request, response);
			return;
		}

		Long memberId = jwtProvider.getMemberId(token);
		Role role = jwtProvider.getRole(token);

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, null,
			List.of(new SimpleGrantedAuthority(role.getKey())));

		authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		filterChain.doFilter(request, response);

	}
}
