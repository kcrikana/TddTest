package com.example.board.dto;

import lombok.Builder;
import lombok.Getter;


@Getter
public class ResponseMemberDto {
	public Long id;
	public String email;
	private String password;
	public String name;
	private String tel;

	@Builder
	public ResponseMemberDto(Long id, String email, String password, String name, String tel) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.tel = tel;
	}
}
