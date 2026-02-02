package com.example.board.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseBoardDto {

	private Long id;

	private String title;

	private String content;

	private Long writerId;


	@Builder
	public ResponseBoardDto(Long id, String title, String content, Long writerId) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.writerId = writerId;
	}
}
