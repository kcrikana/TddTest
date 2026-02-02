package com.example.board.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseCommentDto {

	private Long commentId;

	private String content;

	private Long replyId;

	private Long writterId;

	private Long boardId;


	@Builder
	public ResponseCommentDto(Long commentId, String content, Long replyId, Long writterId,
		Long boardId) {
		this.commentId = commentId;
		this.content = content;
		this.replyId = replyId;
		this.writterId = writterId;
		this.boardId = boardId;
	}
}
