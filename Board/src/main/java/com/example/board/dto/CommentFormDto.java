package com.example.board.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentFormDto {

	private String content;

	private Long replyId;


	@Builder
	public CommentFormDto(String content, Long replyId) {
		this.content = content;
		this.replyId = replyId;
	}
}
