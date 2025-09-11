package com.example.board;


import com.example.board.domain.Board;
import com.example.board.domain.User;
import com.example.board.dto.BoardFormDto;
import com.example.board.dto.ResponseBoardDto;
import com.example.board.dto.ResponseUserDto;
import com.example.board.repository.BoardRepository;
import com.example.board.service.BoardService;
import com.example.board.service.UserService;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardServiceTests {

	@Autowired
	private BoardService boardService;

	@Autowired
	private UserService userService;

	@Autowired
	private BoardRepository boardRepository;

	@Test
	@DisplayName("게시판 글 작성")
	public void createBoard() throws Exception {

		// given
		BoardFormDto boardFormDto = new BoardFormDto("테스트", "이제 되나?");

		// when
		Long boardId = boardService.saveBoard(1L, boardFormDto);


		//then
		ResponseBoardDto responseBoardDto = boardService.findBoardById(boardId);
		Assertions.assertEquals(responseBoardDto.getId(), boardId);
		Assertions.assertEquals(responseBoardDto.getTitle(), "테스트");
		Assertions.assertEquals(responseBoardDto.getContent(), "이제 되나?");

	}

	@Test
	@DisplayName("게시판 한 건 불러오기")
	public void findByIdBoard() throws Exception {

		// given
		Long boardId = 1L;

		// when
		ResponseBoardDto responseBoardDto = boardService.findBoardById(boardId);

		//then
		Assertions.assertEquals(responseBoardDto.getId(), boardId);
		Assertions.assertEquals(responseBoardDto.getTitle(), "테스트");
		Assertions.assertEquals(responseBoardDto.getContent(), "테스트입니다.");
	}


	@Test
	@DisplayName("게시판 전체 불러오기")
	public void findAllBoard() throws Exception {

		// given


		// when

		List<ResponseBoardDto> responseBoardDtos = boardService.findBoardAll();

		//then
		Assertions.assertEquals(responseBoardDtos.size(), 4);
		Assertions.assertEquals(responseBoardDtos.size(), 4);

	}

	@Test
	@DisplayName("게시판 수정")
	public void updateBoard() throws Exception {
		// 유저 아이디 일치 시에만 변경
		
		// given
		Long userId = 1L;
		Long boardId = 4L;
		BoardFormDto boardFormDto = new BoardFormDto("수정", "수정입니다.");


		// when
		Long updateBoardId = boardService.updateBoard(boardId, userId, boardFormDto);


		// then
		ResponseBoardDto responseBoardDto = boardService.findBoardById(boardId);
		Assertions.assertEquals(updateBoardId, boardId);
		Assertions.assertEquals(responseBoardDto.getTitle(), "수정");
		Assertions.assertEquals(responseBoardDto.getContent(), "수정입니다.");

	}


	@Test
	@DisplayName("게시판 삭제")
	public void deleteBoard() throws Exception {
		// 유저 아이디 일치 시 삭제 후 true 리턴
		// 아닐 시 false 리턴
		
		// given
		Long userId = 2L;
		Long boardId = 2L;


		// when
		boolean isTruth = boardService.deleteBoard(boardId, userId);
		boolean isFalse = boardService.deleteBoard(boardId, userId);


		// then

		Assertions.assertEquals(isTruth, true);
		Assertions.assertEquals(isFalse, false);

	}

}
