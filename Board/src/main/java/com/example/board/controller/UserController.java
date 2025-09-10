package com.example.board.controller;


import com.example.board.domain.User;
import com.example.board.dto.ResponseUserDto;
import com.example.board.dto.UserDto;
import com.example.board.service.UserService;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/joinUser")
	public ResponseEntity<Object> joinUser(@RequestBody UserDto userDto) {
		userService.join(userDto);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	@GetMapping("/searchUser/{userId}")
	public ResponseEntity<ResponseUserDto> findUser(@PathVariable Long id) {
		ResponseUserDto responseUserDto = userService.findUserById(id);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<>(responseUserDto, headers, HttpStatus.OK);

	}




}
