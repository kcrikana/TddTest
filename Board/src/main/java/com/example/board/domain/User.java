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
public class User extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private Long id;
	private String email;
	private String password;
	private String name;
	private String tel;
	@Builder
	public User(String email, String password, String name, String tel) {
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
