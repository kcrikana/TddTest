package com.example.board.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
	uniqueConstraints = @UniqueConstraint(
		name = "uk_socialType_providerId",
		columnNames = {"socialType", "socialId"}
	)
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MEMBER_ID")
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private SocialType socialType;

	@Column(nullable = false)
	private String socialId;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String tel;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role = Role.USER;

	@Embedded
	private Adress adress;

	@Column(length = 500)
	private String refreshToken;

	@Builder
	public Member(SocialType socialType, String socialId, String name,
		String tel, Adress adress) {
		this.socialType = socialType;
		this.socialId = socialId;
		this.name = name;
		this.tel = tel;
		this.adress = adress;
		this.role = Role.USER;
	}

	public void updateProfile(String name, String tel, Adress adress) {
		this.name = name;
		this.tel = tel;
		this.adress = adress;
	}

	public void updateRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public void clearRefreshToken() {
		this.refreshToken = null;
	}
}
