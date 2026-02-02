package com.example.board.util;

import org.springframework.http.HttpStatus;

public record ErrorResponse(
	int status,
	String error,
	String message
) {
	public static ErrorResponse of(HttpStatus status, String error, String message) {
		return new ErrorResponse(status.value(), error, message);
	}
}
