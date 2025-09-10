package com.example.board.dto;

import lombok.Builder;
import lombok.Getter;


@Getter
public class ResponseUserDto {
	public Long id;
	public String email;
	private String password;
	public String name;
	private String tel;

	@Builder
	public ResponseUserDto(Long id, String email, String password, String name, String tel) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.tel = tel;
	}
}
