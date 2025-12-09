package com.example.board.dto;

import com.example.board.domain.Adress;
import com.example.board.domain.Member;
import lombok.Builder;
import lombok.Getter;


// 유저 정보 리턴

@Getter
public class ResponseMemberDto {
	public Long id;
	public String email;
	private String password;
	public String name;
	private String tel;
	private Adress adress;

	public ResponseMemberDto(Member member) {
		this.id = member.getId();
		this.email = member.getEmail();
		this.password = member.getPassword();
		this.name = member.getName();
		this.tel = member.getTel();
		this.adress = member.getAdress();
	}

	@Builder
	public ResponseMemberDto(Long id, String email, String password, String name, String tel, Adress adress) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.tel = tel;
		this.adress = adress;
	}
}
