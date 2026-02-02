package com.example.board.util;


import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

	public ResponseCookie refreshCookie(String name, String value, long maxAgeSeconds, boolean secure) {
		return ResponseCookie.from(name, value)
			.httpOnly(true)
			.secure(secure)
			.path("/")
			.maxAge(maxAgeSeconds)
			.sameSite("None")
			.build();

	}

	public ResponseCookie deleteCookie(String name, boolean secure) {
		return ResponseCookie.from(name, "")
			.httpOnly(true)
			.secure(secure)
			.path("/")
			.maxAge(0)
			.sameSite("None")
			.build();

	}
}
