package com.example.board;

import com.example.board.domain.User;
import com.example.board.dto.ResponseUserDto;
import com.example.board.dto.UserDto;
import com.example.board.repository.UserRepository;
import com.example.board.service.UserService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class UserServiceTests {


	@Autowired
	private UserService userService;

	@Autowired
	private EntityManager entityManager;

	@Test
	@DisplayName("유저 불러오기")
	public void findUserById() throws Exception {
		// given
		Long userId = 1L;

		// when
		ResponseUserDto responseUserDto = userService.findUserById(userId);


		// then
		Assertions.assertEquals(responseUserDto.getId(), userId);

	}

	@Test
	@DisplayName("회원가입")
	public void joinUser() throws Exception {
		//given
		UserDto userDto = UserDto.builder()
			.email("asd@google.com")
			.password("1234")
			.name("김꽂게")
			.tel("010-1234-5678")
			.build();

		// when
		Long userId = userService.join(userDto);
		ResponseUserDto responseUserDto = userService.findUserById(userId);

		// then
		Assertions.assertEquals(responseUserDto.getName(), "김꽂게");
	}
}
