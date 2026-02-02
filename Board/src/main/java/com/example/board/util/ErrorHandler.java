package com.example.board.util;

import com.example.board.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

	// =========================
	// 401 Unauthorized
	// =========================
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ErrorResponse> handleUnauthorized(
		UnauthorizedException ex,
		HttpServletRequest request
	) {
		log.warn("Unauthorized access: {}, uri={}", ex.getMessage(), request.getRequestURI());

		return ResponseEntity
			.status(HttpStatus.UNAUTHORIZED)
			.body(ErrorResponse.of(
				HttpStatus.UNAUTHORIZED,
				"UNAUTHORIZED",
				"인증이 필요합니다."
			));
	}

	// =========================
	// 400 Bad Request
	// =========================
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleBadRequest(
		IllegalArgumentException ex,
		HttpServletRequest request
	) {
		log.warn("Bad request: {}, uri={}", ex.getMessage(), request.getRequestURI());

		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(ErrorResponse.of(
				HttpStatus.BAD_REQUEST,
				"BAD_REQUEST",
				ex.getMessage()
			));
	}

	// =========================
	// 500 Internal Server Error
	// =========================
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(
		Exception ex,
		HttpServletRequest request
	) {
		log.error("Unexpected error: {}, uri={}", ex.getMessage(), request.getRequestURI(), ex);

		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(ErrorResponse.of(
				HttpStatus.INTERNAL_SERVER_ERROR,
				"INTERNAL_SERVER_ERROR",
				"서버 오류가 발생했습니다."
			));
	}
}
