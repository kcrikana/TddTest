package com.example.board.controller;


import com.example.board.dto.CommentFormDto;
import com.example.board.dto.ResponseCommentDto;
import com.example.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

	private final CommentService commentService;
	
	// 댓글 저장
	@PostMapping("/{boardId}")
	public ResponseEntity<Long> saveComment(Authentication authentication, @PathVariable Long boardId, @RequestBody CommentFormDto commentFormDto) {
		Long memberId = (Long) authentication.getPrincipal() ;
		return ResponseEntity.status(HttpStatus.CREATED).body(commentService.saveComment(memberId, boardId, commentFormDto));
	}

	
	
	// 댓글 수정
	@PutMapping("/{boardId}/{commentId}")
	public ResponseEntity<Void> updateComment(Authentication authentication, @PathVariable Long boardId, @PathVariable Long commentId,@RequestBody CommentFormDto commentFormDto) {
		Long memberId = (Long) authentication.getPrincipal();
		commentService.updateComment(memberId, boardId, commentId, commentFormDto);
		return ResponseEntity.noContent().build();
	}
	
	
	// 댓글 삭제
	@DeleteMapping("/{commentId}")
	public ResponseEntity<Void> deleteComment(Authentication authentication, @PathVariable Long commentId) {
		Long memberId = (Long) authentication.getPrincipal();


		boolean deleted = commentService.deleteComment(memberId, commentId);
		if(!deleted) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

		return ResponseEntity.noContent().build();
	}

}
