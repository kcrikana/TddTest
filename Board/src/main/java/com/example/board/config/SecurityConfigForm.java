package com.example.board.config;

import com.example.board.util.CustomFailureHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfigForm {

	private final CustomFailureHandler customFailureHandler;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/", "/members/login", "/members/join", "/loginProc").permitAll()
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
				.requestMatchers("/post").hasRole("USER")
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().authenticated()
			)
			.formLogin((formLogin) -> formLogin
				.loginPage("/members/login")
				.loginProcessingUrl("/loginProc")
				.usernameParameter("email")
				.passwordParameter("password")
				.defaultSuccessUrl("/members/info", true)
				.failureHandler(customFailureHandler)
				.permitAll()
			)
			.logout((logout) -> logout
				.logoutUrl("/logoutProc")
				.logoutSuccessUrl("/")
				.permitAll()
			);
		return http.build();
	}
}
