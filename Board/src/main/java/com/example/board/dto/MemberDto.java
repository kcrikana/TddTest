package com.example.board.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

// 유저 회원가입
@Getter
@Setter
public class MemberDto {

    @NotEmpty(message = "이메일을 입력해주세요.")
    private String email;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotEmpty(message = "이름을 입력해주세요.")
    private String name;

    @NotEmpty(message = "전화번호를 입력해주세요.")
    private String tel;

    @NotEmpty(message = "도시를 입력해주세요.")
    private String city;

    @NotEmpty(message = "상세주소를 입력해주세요.")
    private String street;

    private String state;

    @NotEmpty(message = "우편번호를 입력해주세요.")
    private String zipCode;
}
