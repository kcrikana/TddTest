package com.example.board.service;


import com.example.board.domain.Board;
import com.example.board.domain.Comment;
import com.example.board.domain.Member;
import com.example.board.dto.CommentFormDto;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.CommentRepository;
import com.example.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final BoardRepository boardRepository;
	private final MemberRepository memberRepository;
	private final CommentRepository commentRepository;


/*
	댓글 CUD 작성
	Research는 아직 생각 안함

 */


	// 댓글 작성
	@Transactional
	public Long saveComment(Long memberId, Long boardId, CommentFormDto commentFormDto) {
		Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
		Board board = boardRepository.findOneBoardById(boardId).orElseThrow(() -> new IllegalArgumentException("게시판을 찾을 수 없습니다."));
		Comment comment = Comment.builder()
			.content(commentFormDto.getContent())
			.replyId(commentFormDto.getReplyId())
			.board(board)
			.member(member)
			.build();
		commentRepository.save(comment);
		return comment.getId();
	}



	// 댓글 수정
	@Transactional
	public Long updateComment(Long memberId, Long boardId, Long commentId,CommentFormDto commentFormDto) {
		Comment comment = commentRepository.findOneCommentById(commentId).orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
		if(comment.getMember().getId().equals(memberId) && comment.getBoard().getId().equals(boardId)) comment.update(commentFormDto.getContent());
		return comment.getId();
	}


	// 댓글 삭제
	@Transactional
	public boolean deleteComment(Long memberId, Long commentId) {
		Comment comment = commentRepository.findOneCommentById(commentId).orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
		if(comment.getMember().getId().equals(memberId)) {
			commentRepository.deleteById(commentId);
			return true;
		}
		return false;
	}


}
