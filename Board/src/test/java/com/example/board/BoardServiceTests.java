package com.example.board;

import com.example.board.domain.Adress;
import com.example.board.domain.Board;
import com.example.board.domain.Member;
import com.example.board.domain.Role;
import com.example.board.dto.BoardFormDto;
import com.example.board.dto.ResponseBoardDetailDto;
import com.example.board.dto.ResponseBoardDto;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.MemberRepository;
import com.example.board.service.BoardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


// 보드 부분 통합 테스트입니다.
@SpringBootTest
@Transactional
class BoardServiceTests {


    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Member member;
    private Member anotherMember;

	@BeforeEach
	void setUp() {
		// Adress 생성자를 올바르게 수정했습니다.
		Adress adress = new Adress("도시", "거리", "상세주소", "12345");
		member = Member.builder()
			.name("testuser")
			.tel("010-1234-5678")
			.adress(adress)
			.build();
		memberRepository.save(member);

		anotherMember = Member.builder()
			.name("anotheruser")
			.tel("010-8765-4321")
			.adress(adress)
			.build();
		memberRepository.save(anotherMember);
	}

    private Board createAndSaveBoard(String title, String content, Member boardMember) {
        Board board = Board.builder()
                .title(title)
                .content(content)
                .member(boardMember)
                .build();
        return boardRepository.save(board);
    }

    @Test
    @DisplayName("게시판 글 작성")
    void createBoard() {
        // given
        BoardFormDto boardFormDto = new BoardFormDto("테스트 제목", "테스트 내용");

        // when
        Long boardId = boardService.saveBoard(member.getId(), boardFormDto);

        // then
        ResponseBoardDto responseBoardDto = boardService.findBoardById(boardId).getResponseBoardDto();
        assertEquals(boardId, responseBoardDto.getId());
        assertEquals("테스트 제목", responseBoardDto.getTitle());
        assertEquals("테스트 내용", responseBoardDto.getContent());
    }

    @Test
    @DisplayName("게시판 한 건 불러오기")
    void findByIdBoard() {
        // given
        Board savedBoard = createAndSaveBoard("찾을 제목", "찾을 내용", member);

        // when
	    ResponseBoardDto responseBoardDto = boardService.findBoardById(savedBoard.getId()).getResponseBoardDto();

        // then
        assertEquals(savedBoard.getId(), responseBoardDto.getId());
        assertEquals("찾을 제목", responseBoardDto.getTitle());
        assertEquals("찾을 내용", responseBoardDto.getContent());
    }

    @Test
    @DisplayName("게시판 전체 불러오기")
    void findAllBoard() {
        // given
        createAndSaveBoard("제목 1", "내용 1", member);
        createAndSaveBoard("제목 2", "내용 2", member);

        // when
        List<ResponseBoardDto> responseBoardDtos = boardService.findBoardAll();

        // then
        assertEquals(2, responseBoardDtos.size());
    }

    @Test
    @DisplayName("게시판 수정 - 성공")
    void updateBoard_Success() {
        // given
        Board savedBoard = createAndSaveBoard("원본 제목", "원본 내용", member);
        BoardFormDto boardFormDto = new BoardFormDto("수정된 제목", "수정된 내용");

        // when
        Long updatedBoardId = boardService.updateBoard(savedBoard.getId(), member.getId(), boardFormDto);

        // then
        ResponseBoardDto responseBoardDto = boardService.findBoardById(updatedBoardId).getResponseBoardDto();
        assertEquals(savedBoard.getId(), updatedBoardId);
        assertEquals("수정된 제목", responseBoardDto.getTitle());
        assertEquals("수정된 내용", responseBoardDto.getContent());
    }

    @Test
    @DisplayName("게시판 수정 - 실패 (작성자가 다른 경우)")
    void updateBoard_Fail_DifferentMember() {
        // given
        Board savedBoard = createAndSaveBoard("원본 제목", "원본 내용", member);
        BoardFormDto boardFormDto = new BoardFormDto("수정된 제목", "수정된 내용");

        // when
        boardService.updateBoard(savedBoard.getId(), anotherMember.getId(), boardFormDto);

        // then
        ResponseBoardDto responseBoardDto = boardService.findBoardById(savedBoard.getId()).getResponseBoardDto();
        assertEquals("원본 제목", responseBoardDto.getTitle()); // 제목이 변경되지 않았는지 확인
        assertEquals("원본 내용", responseBoardDto.getContent()); // 내용이 변경되지 않았는지 확인
    }


    @Test
    @DisplayName("게시판 삭제 - 성공")
    void deleteBoard_Success() {
        // given
        Board savedBoard = createAndSaveBoard("삭제할 제목", "삭제할 내용", member);

        // when
        boolean result = boardService.deleteBoard(savedBoard.getId(), member.getId());

        // then
        assertTrue(result);
        assertThrows(IllegalArgumentException.class, () -> {
            boardService.findBoardById(savedBoard.getId());
        });
    }

    @Test
    @DisplayName("게시판 삭제 - 실패 (작성자가 다른 경우)")
    void deleteBoard_Fail_Differentember() {
        // given
        Board savedBoard = createAndSaveBoard("삭제되지 않을 제목", "삭제되지 않을 내용", member);

        // when
        boolean result = boardService.deleteBoard(savedBoard.getId(), anotherMember.getId());

        // then
        assertFalse(result);
        assertNotNull(boardRepository.findById(savedBoard.getId()));
    }
}
