package com.example.board.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MEMBER_ID")
	private Long id;

	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false, unique = false)
	private String password;
	@Column(nullable = false, unique = false)
	private String name;
	private String tel;
	@Builder
	public Member(String email, String password, String name, String tel) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.tel = tel;
	}

	public void update(String email, String password, String name) {
		this.email = email;
		this.password = password;
		this.name = name;
	}
}
