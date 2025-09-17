package com.example.board.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
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
	@Column(nullable = false, unique = false)
	private String tel;

//	@AttributeOverrides({
//		@AttributeOverride(name = "street", column = @Column(name = "home"))
//	})

	@Enumerated
	private Role role;

	@Embedded
	private Adress adress;

	@Builder
	public Member(String email, String password, String name, String tel, Adress adress) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.tel = tel;
		this.adress = adress;
		this.role = Role.USER;
	}

	// 사용자 권한 수정
	public void autorizeMember() {
		this.role = Role.USER;
	}


	public void update(String email, String password, String name) {
		this.email = email;
		this.password = password;
		this.name = name;
	}
}
