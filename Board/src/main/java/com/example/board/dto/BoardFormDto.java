package com.example.board.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardFormDto {
	@NotEmpty(message = "제목을 입력해주세요.")
	String title;
	@NotEmpty(message = "게시글을 작성해주세요.")
	String content;

	@Builder
	public BoardFormDto(String title, String content) {
		this.title = title;
		this.content = content;
	}
}
