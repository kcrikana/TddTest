package com.example.board.service;

import com.example.board.domain.Member;
import com.example.board.domain.Role;
import com.example.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService implements UserDetailsService {

	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Member member = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("해당 이메일이 존재하지 않습니다."));
		return User.builder()
			.username(member.getEmail())
			.password(member.getPassword())
			.roles(member.getRole().name())
			.build();
	}

}
