package com.example.board;

import com.example.board.dto.ResponseMemberDto;
import com.example.board.dto.MemberDto;
import com.example.board.service.MemberService;
import jakarta.persistence.EntityManager;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberServiceTests {


	@Autowired
	private MemberService memberService;

	@Autowired
	private EntityManager entityManager;

	@Test
	@DisplayName("유저 불러오기")
	public void findMemberById() throws Exception {
		// given
		Long memberId = 3L;

		// when
		ResponseMemberDto responseMemberDto = memberService.findMemberById(memberId);
		// then
		Assertions.assertEquals(responseMemberDto.getId(), memberId);

	}

	@Test
	@DisplayName("회원가입")
	public void joinMember() throws Exception {
		//given


		// when


		// then

	}
}
