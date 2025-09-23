package com.example.board.config;


import com.example.board.repository.MemberRepository;
import com.example.board.service.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.HttpSecurityDsl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;


//@Configuration
//@EnableWebSecurity
public class SecurityConfig {
//
//
//	@Bean
//	public BCryptPasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		http
//			.cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
//				@Override
//				public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
//					CorsConfiguration configuration = new CorsConfiguration();
////					configuration.setAllowedOrigins(Arrays.asList());       // 설정 URL(프론트)
//					configuration.setAllowedMethods(Collections.singletonList("*"));
//					configuration.setAllowCredentials(true);
//					configuration.setAllowedHeaders((Collections.singletonList("*")));
//					configuration.setMaxAge(3600L);
//					return configuration;
//				}
//
//			}))
//			.csrf(AbstractHttpConfigurer::disable)
//			.httpBasic(AbstractHttpConfigurer::disable)
//			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
////			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
////			.formLogin(AbstractHttpConfigurer::)
//	}

}