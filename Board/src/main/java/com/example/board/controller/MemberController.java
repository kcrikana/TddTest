package com.example.board.controller;


import com.example.board.dto.ResponseMemberDto;
import com.example.board.dto.MemberDto;
import com.example.board.service.MemberService;
import java.nio.charset.Charset;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

//	@PostMapping("/joinMember")
//	public ResponseEntity<Object> joinMember(@RequestBody MemberDto memberDto) {
//		memberService.join(memberDto);
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//		return new ResponseEntity<>(headers, HttpStatus.CREATED);
//	}

	@GetMapping("/searchMember/{memberId}")
	public ResponseEntity<ResponseMemberDto> findMember(@PathVariable Long id) {
		ResponseMemberDto responseMemberDto = memberService.findMemberById(id);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<>(responseMemberDto, headers, HttpStatus.OK);

	}

}
