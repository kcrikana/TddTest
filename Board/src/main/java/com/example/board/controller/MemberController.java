package com.example.board.controller;


import com.example.board.dto.ResponseMemberDto;
import com.example.board.dto.MemberDto;
import com.example.board.service.MemberService;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;
	// private final LoginService loginService;

	// 회원 가입
	@PostMapping("/register")
	public ResponseEntity<Long> registerMember(@RequestBody MemberDto memberDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(memberService.saveMember(memberDto));
	}

	// 모든 회원 조회
	@GetMapping("/members")
	public ResponseEntity<List<ResponseMemberDto>> findAllMember() {
		return ResponseEntity.ok(memberService.findAllMember());
	}

	// 특정 회원 조회
	@GetMapping("/members/{memberId}")
	public ResponseEntity<ResponseMemberDto> findMember(@PathVariable("memberId") Long memberId) {
		return ResponseEntity.ok(memberService.findMemberById(memberId));
	}

	// 회원 정보 수정
	@PutMapping("/members/{memberId}")
	public ResponseEntity<Long> updateMember(@PathVariable("memberId") Long memberId, @RequestBody MemberDto memberDto) {
		return ResponseEntity.ok(memberService.updateMember(memberId, memberDto));
	}

	// 회원 탈퇴
	@DeleteMapping("/members/{memberId}")
	public ResponseEntity<Void> deleteMember(@PathVariable("memberId") Long memberId) {
		memberService.deleteMember(memberId);
		return ResponseEntity.noContent().build();
	}

}
