package com.example.board;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.example.board.domain.Adress;
import com.example.board.domain.Board;
import com.example.board.domain.Member;
import com.example.board.dto.ResponseBoardDto;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.MemberRepository;
import com.example.board.service.BoardService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


// 서비스 단위의 단위 테스트입니다.
@ExtendWith(MockitoExtension.class)
public class BoardServiceUnitTests {

	@InjectMocks
	private BoardService boardService;

	@Mock
	private BoardRepository boardRepository;

	@Mock
	private MemberRepository memberRepository;

	private Member member = mock(Member.class);
	private Member anotherMember;

	@BeforeEach
	void setUp() {
		Adress adress = new Adress("도시", "거리", "상세주소", "12345");
		member = Member.builder()
			.name("testuser")
			.email("test@example.com")
			.password("password")
			.tel("010-1234-5678")
			.adress(adress)
			.build();
		memberRepository.save(member);

		anotherMember = Member.builder()
			.name("anotheruser")
			.email("another@example.com")
			.password("password")
			.tel("010-8765-4321")
			.adress(adress)
			.build();
		memberRepository.save(anotherMember);
	}

	@Test
	@DisplayName("게시글 한 건 불러오기 - 성공")
	void findByIdBoard() {
		// given
		Long boardId = 1L;
		Board board =  mock(Board.class);
		given(board.getId()).willReturn(boardId);
		given(board.getTitle()).willReturn("찾을 제목");
		given(board.getContent()).willReturn("찾을 내용");
		given(boardRepository.findById(boardId)).willReturn(Optional.of(board));

		// when
		ResponseBoardDto responseBoardDto = boardService.findBoardById(boardId);
		// then
		assertEquals(boardId, responseBoardDto.getId());
		assertEquals("찾을 제목", responseBoardDto.getTitle());
		assertEquals("찾을 내용", responseBoardDto.getContent());


	}





}
