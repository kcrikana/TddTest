package com.example.board.dto;

import com.example.board.domain.Adress;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;


// 유저 회원가입
@Getter
public class MemberDto {

	@NotEmpty(message = "이메일을 입력해주세요.")
	private final String email;
	@NotEmpty(message = "비밀번호를 입력해주세요.")
	private final String password;
	@NotEmpty(message = "이름을 입력해주세요.")
	private final String name;
	@NotEmpty(message = "전화번호를 입력해주세요.")
	private final String tel;

	@NotEmpty(message = "주소를 입력해주세요.")
	private final String city;
	@NotEmpty(message = "주소를 입력해주세요.")
	private final String street;
	@NotEmpty(message = "주소를 입력해주세요.")
	private final String state;
	@NotEmpty(message = "주소를 입력해주세요.")
	private final String zipCode;

	@Builder
	public MemberDto(String email, String password, String name, String tel, String city,
		String street,
		String state, String zipCode) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.tel = tel;
		this.city = city;
		this.street = street;
		this.state = state;
		this.zipCode = zipCode;
	}
}
