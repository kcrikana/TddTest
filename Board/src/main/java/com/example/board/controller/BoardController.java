package com.example.board.controller;

import com.example.board.dto.BoardFormDto;
import com.example.board.dto.ResponseBoardDetailDto;
import com.example.board.dto.ResponseBoardDto;
import com.example.board.service.BoardService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

	// 모든 게시글 조회
    @GetMapping("")
    public ResponseEntity<List<ResponseBoardDto>> findAllBoard() {
        return ResponseEntity.ok(boardService.findBoardAll());
    }

    // 특정 게시글 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<ResponseBoardDetailDto> findBoard(@PathVariable("boardId") Long boardId) {
        return ResponseEntity.ok(boardService.findBoardById(boardId));
    }


	// 게시글 저장
    @PostMapping("")
    public ResponseEntity<Long> saveBoard(Authentication authentication, @RequestBody BoardFormDto dto) {
	    Long memberId = (Long) authentication.getPrincipal();
	    return ResponseEntity.status(HttpStatus.CREATED).body(boardService.saveBoard(memberId, dto));
    }

	// 게시글 수정
	@PutMapping("/{boardId}")
	public ResponseEntity<Void> updateBoard(
		Authentication authentication,
		@PathVariable("boardId") Long boardId,
		@RequestBody BoardFormDto boardFormDto
	) {
		Long memberId = (Long) authentication.getPrincipal();
		boardService.updateBoard(boardId, memberId, boardFormDto);
		return ResponseEntity.noContent().build();
	}

	// 게시글 삭제
	@DeleteMapping("/{boardId}")
	public ResponseEntity<Void> deleteBoard(
		Authentication authentication,
		@PathVariable("boardId") Long boardId
	) {
		Long memberId = (Long) authentication.getPrincipal();
		boolean deleted = boardService.deleteBoard(boardId, memberId);
		if(!deleted) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		return ResponseEntity.noContent().build();
	}
}
