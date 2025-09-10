package com.example.board.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseBoardDto {

	private Long id;

	private String title;

	private String content;

	@Builder
	public ResponseBoardDto(Long id, String title, String content) {
		this.id = id;
		this.title = title;
		this.content = content;
	}
}
