package com.example.board.service;


import com.example.board.domain.Member;
import com.example.board.dto.ResponseMemberDto;
import com.example.board.dto.MemberDto;
import com.example.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	// security 추가
	private final MemberRepository memberRepository;

	@Transactional
	public Long join(MemberDto memberDto) {
		Member member = Member.builder()
			.name(memberDto.getName())
			.email(memberDto.getEmail())
			.password(memberDto.getPassword())
			.tel(memberDto.getTel())
			.build();
		memberRepository.save(member);
		return member.getId();
	}

	public ResponseMemberDto findMemberById(Long id) {
		Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
		ResponseMemberDto responseMemberDto = ResponseMemberDto.builder()
			.id(member.getId())
			.name(member.getName())
			.email(member.getEmail())
			.tel(member.getTel())
			.build();
		return responseMemberDto;
	}

}
