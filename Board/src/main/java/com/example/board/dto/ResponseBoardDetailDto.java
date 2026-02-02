package com.example.board.dto;


import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseBoardDetailDto {

	private ResponseBoardDto responseBoardDto;

	private List<ResponseCommentDto> responseCommentDtos;

	@Builder
	public ResponseBoardDetailDto(ResponseBoardDto responseBoardDto,
		List<ResponseCommentDto> responseCommentDtos) {
		this.responseBoardDto = responseBoardDto;
		this.responseCommentDtos = responseCommentDtos;
	}

}
