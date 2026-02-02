package com.example.board.service;


import com.example.board.domain.Board;
import com.example.board.domain.Comment;
import com.example.board.domain.Member;
import com.example.board.dto.BoardFormDto;
import com.example.board.dto.CommentFormDto;
import com.example.board.dto.ResponseBoardDetailDto;
import com.example.board.dto.ResponseBoardDto;
import com.example.board.dto.ResponseCommentDto;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.CommentRepository;
import com.example.board.repository.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;
	private final MemberRepository memberRepository;
	private final CommentRepository commentRepository;

	/*
		게시판 CRUD

	 */


	// 게시판 만들기
	@Transactional
	public Long saveBoard(Long memberId, BoardFormDto boardFormDto) {
		Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
		Board board = Board.builder()
			.title(boardFormDto.getTitle())
			.content(boardFormDto.getContent())
			.member(member)
			.build();
		boardRepository.save(board);
		return board.getId();
	}



	// 게시판 전체 검색
	@Transactional(readOnly = true)
	public List<ResponseBoardDto> findBoardAll() {
		List<ResponseBoardDto> responseBoardDtos = new ArrayList<>();
		for(Board board : boardRepository.findAllBoardList()) {
			responseBoardDtos.add(new ResponseBoardDto(board.getId(), board.getTitle(),
				board.getContent(), board.getMember().getId()));
		}
		return responseBoardDtos;
	}


	// 한건 게시판 검색 및 댓글 전체 불러오기
	@Transactional(readOnly = true)
	public ResponseBoardDetailDto findBoardById(Long id) {
		Board board = boardRepository.findOneBoardById(id).orElseThrow(() -> new IllegalArgumentException("게시판을 찾을 수 없습니다."));
		List<ResponseCommentDto> comments = new ArrayList<>();
		for(Comment comment : commentRepository.findByBoardIdOrderByCreatedAtAsc(id)) {
			comments.add(new ResponseCommentDto(comment.getId(), comment.getContent(), comment.getReplyId(), comment.getBoard()
				.getId(), comment.getMember().getId()));

		}
		return new ResponseBoardDetailDto(new ResponseBoardDto(board.getId(), board.getTitle(), board.getContent(), board.getMember().getId()), comments);
	}


	// 게시판 수정
	@Transactional
	public Long updateBoard(Long boardId, Long memberId, BoardFormDto boardFormDto) {
		Board board = boardRepository.findOneBoardById(boardId).orElseThrow(() -> new IllegalArgumentException("게시판을 찾을 수 없습니다."));
		if(board.getMember().getId().equals(memberId)) board.update(boardFormDto.getTitle(), boardFormDto.getContent());
		return boardId;
	}


	// 게시판 삭제
	@Transactional
	public boolean deleteBoard(Long boardId, Long memberId) {
		Board board = boardRepository.findOneBoardById(boardId).orElseThrow(() -> new IllegalArgumentException("게시판을 찾을 수 없습니다."));
		if(board.getMember().getId().equals(memberId)) {
			boardRepository.deleteById(boardId);
			return true;
		}
		return false;
	}

}
