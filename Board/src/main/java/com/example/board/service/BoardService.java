package com.example.board.service;


import com.example.board.domain.Board;
import com.example.board.domain.User;
import com.example.board.dto.BoardFormDto;
import com.example.board.dto.ResponseBoardDto;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;
	private final UserRepository userRepository;

	/*
		게시판 CRUD

	 */


	// 게시판 만들기
	@Transactional
	public Long saveBoard(Long userId, BoardFormDto boardFormDto) {
		User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
		Board board = Board.builder()
			.title(boardFormDto.getTitle())
			.content(boardFormDto.getContent())
			.user(user)
			.build();
		boardRepository.save(board);
		return board.getId();
	}



	// 게시판 전체 검색
	public List<ResponseBoardDto> findBoardAll() {
		List<ResponseBoardDto> responseBoardDtos = new ArrayList<>();
		for(Board board : boardRepository.findAll()) {
			responseBoardDtos.add(new ResponseBoardDto(board.getId(), board.getTitle(),
				board.getTitle()));
		}
		return responseBoardDtos;
	}



	// 한건 게시판 검색
	public ResponseBoardDto findBoardById(Long id) {
		Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시판을 찾을 수 없습니다."));
		return new ResponseBoardDto(board.getId(), board.getTitle(), board.getContent());
	}


	// 게시판 수정
	@Transactional
	public Long updateBoard(Long boardId, Long userId, BoardFormDto boardFormDto) {
		Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("게시판을 찾을 수 없습니다."));
		if(board.getUser().getId().equals(userId)) board.update(boardFormDto.getTitle(), boardFormDto.getContent());
		return boardId;
	}


	// 게시판 삭제
	@Transactional
	public boolean deleteBoard(Long boardId, Long userId) {
		Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("게시판을 찾을 수 없습니다."));
		if(board.getUser().getId().equals(userId)) {
			boardRepository.deleteById(boardId);
			return true;
		}
		else return false;
	}

}
