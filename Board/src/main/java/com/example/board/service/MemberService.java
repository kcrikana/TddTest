package com.example.board.service;

import com.example.board.domain.Adress;
import com.example.board.domain.Member;
import com.example.board.dto.MemberDto;
import com.example.board.dto.ResponseMemberDto;
import com.example.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Transactional
	public Long join(MemberDto memberDto) {
		validateDuplicateMember(memberDto.getEmail());

		Adress adress = new Adress(memberDto.getCity(), memberDto.getStreet(), memberDto.getState(), memberDto.getZipCode());

		Member member = Member.builder()
			.name(memberDto.getName())
			.email(memberDto.getEmail())
			.password(passwordEncoder.encode(memberDto.getPassword()))
			.tel(memberDto.getTel())
			.adress(adress)
			.build();
		memberRepository.save(member);
		return member.getId();
	}

	@Transactional(readOnly = true)
	public ResponseMemberDto findMemberById(Long id) {
		Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
		ResponseMemberDto responseMemberDto = ResponseMemberDto.builder()
			.id(member.getId())
			.name(member.getName())
			.email(member.getEmail())
			.tel(member.getTel())
			.adress(member.getAdress())
			.build();
		return responseMemberDto;
	}

	@Transactional(readOnly = true)
	public ResponseMemberDto findMemberByEmail(String email) {
		Member member = memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
		ResponseMemberDto responseMemberDto = ResponseMemberDto.builder()
			.id(member.getId())
			.name(member.getName())
			.email(member.getEmail())
			.tel(member.getTel())
			.adress(member.getAdress())
			.build();
		return responseMemberDto;
	}

	private void validateDuplicateMember(String email) {
		memberRepository.findByEmail(email).ifPresent(m -> {
			throw new IllegalStateException("이미 가입된 회원입니다.");
		});
	}
}
