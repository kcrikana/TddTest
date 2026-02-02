package com.example.board.config;


import com.example.board.util.CustomLogoutHandler;
import com.example.board.util.CustomLogoutSuccessHandler;
import com.example.board.util.JwtProvider;
import com.example.board.util.JwtTokenValidatorFilter;
import com.example.board.util.OAuth2LoginFailureHandler;
import com.example.board.util.OAuth2LoginSuccessHandler;
import java.util.Arrays;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final CustomLogoutHandler customLogoutHandler;
	private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
	private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
	private final JwtTokenValidatorFilter jwtTokenValidatorFilter;
	private final CustomLogoutSuccessHandler customLogoutSuccessHandler;


	@Value("${ACCESS_URL}")
	private String url;


	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, JwtProvider jwtProvider,
		JwtTokenValidatorFilter jwtTokenValidatorFilter) throws Exception {
		http
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.csrf(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.oauth2Login(oauth2 -> oauth2
				.successHandler(oAuth2LoginSuccessHandler)
				.failureHandler(oAuth2LoginFailureHandler))
			.logout(logout -> logout
				.logoutUrl("/api/auth/logout")
				.addLogoutHandler(customLogoutHandler)
				.logoutSuccessHandler(customLogoutSuccessHandler))
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.addFilterBefore(jwtTokenValidatorFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/oauth2/**", "/login/oauth2/**", "/error").permitAll()
				.requestMatchers(HttpMethod.GET, "/board/**").permitAll()
				.requestMatchers("/api/auth/refresh").permitAll()
				.requestMatchers("/api/members/signup", "/api/members/login").permitAll()
				.requestMatchers("/api/auth/logout").permitAll()
				.requestMatchers("/api/auth/refresh").permitAll()

				.requestMatchers(HttpMethod.POST, "/board/**").authenticated()
				.requestMatchers(HttpMethod.PUT, "/board/**").authenticated()
				.requestMatchers(HttpMethod.DELETE, "/board/**").authenticated()
				.anyRequest().authenticated()
			);
		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		return request -> {
			CorsConfiguration config = new CorsConfiguration();
			config.setAllowedOrigins(Arrays.asList(url));
			config.setAllowedMethods(Collections.singletonList("*"));
			config.setAllowCredentials(true);
			config.setAllowedHeaders(Collections.singletonList("*"));
			config.setMaxAge(3600L);
			return config;
		};
	}

}
