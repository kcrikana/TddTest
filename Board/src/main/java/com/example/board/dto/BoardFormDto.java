package com.example.board.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardFormDto {

	private String title;

	private String content;

	@Builder
	public BoardFormDto(String title, String content) {
		this.title = title;
		this.content = content;
	}
}
