package com.example.board.service;


import com.example.board.domain.Adress;
import com.example.board.domain.Member;
import com.example.board.dto.ResponseMemberDto;
import com.example.board.dto.MemberDto;
import com.example.board.repository.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	// security 추가
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;



	@Transactional
	public Long saveMember(MemberDto memberDto) {
		Member member = Member.builder()
			.email(memberDto.getEmail())
			.password(passwordEncoder.encode(memberDto.getPassword()))
			.name(memberDto.getName())
			.tel(memberDto.getTel())
			.adress(new Adress(memberDto.getCity(), memberDto.getStreet(), memberDto.getState(), memberDto.getZipCode()))
			.build();
		memberRepository.save(member);
		return member.getId();
	}


	@Transactional(readOnly = true)
	public ResponseMemberDto findMemberById(Long id) {
		Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
		return new ResponseMemberDto(member);
	}


	@Transactional(readOnly = true)
	public List<ResponseMemberDto> findAllMember() {
		List<ResponseMemberDto> responseMemberDtos = new ArrayList<>();
		for(Member member : memberRepository.findAll()) {
			responseMemberDtos.add(new ResponseMemberDto(member));
		}
		return responseMemberDtos;
	}


	@Transactional
	public Long updateMember(Long memberId, MemberDto memberDto) {
		Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
		member.update(memberDto.getEmail(), passwordEncoder.encode(memberDto.getPassword()), memberDto.getName());
		member.getAdress().updateAdress(memberDto.getCity(), memberDto.getStreet(), memberDto.getState(), member.getAdress().getZipCode());
		return member.getId();
	}

	@Transactional
	public void deleteMember(Long memberId) {
		memberRepository.deleteById(memberId);
	}

}
