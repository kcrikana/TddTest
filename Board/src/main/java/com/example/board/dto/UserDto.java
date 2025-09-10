package com.example.board.dto;

import com.example.board.domain.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;


@Getter
public class UserDto {

	@NotEmpty(message = "이메일을 입력해주세요.")
	private final String email;
	@NotEmpty(message = "비밀번호를 입력해주세요.")
	private final String password;
	@NotEmpty(message = "이름을 입력해주세요.")
	private final String name;
	@NotEmpty(message = "전화번호를 입력해주세요.")
	private final String tel;


	@Builder
	public UserDto(String email, String password, String name, String tel) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.tel = tel;
	}

}
