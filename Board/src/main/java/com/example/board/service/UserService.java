package com.example.board.service;


import com.example.board.domain.User;
import com.example.board.dto.ResponseUserDto;
import com.example.board.dto.UserDto;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

	// security 추가
	private final UserRepository userRepository;

	@Transactional
	public Long join(UserDto userDto) {
		User user = User.builder()
			.name(userDto.getName())
			.email(userDto.getEmail())
			.password(userDto.getPassword())
			.tel(userDto.getTel())
			.build();
		userRepository.save(user);
		return user.getId();
	}

	public ResponseUserDto findUserById(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
		ResponseUserDto responseUserDto = ResponseUserDto.builder()
			.id(user.getId())
			.name(user.getName())
			.email(user.getEmail())
			.tel(user.getTel())
			.build();
		return responseUserDto;
	}

}
