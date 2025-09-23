package com.example.board.controller;

import com.example.board.dto.MemberDto;
import com.example.board.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.board.dto.ResponseMemberDto;
import org.springframework.security.core.Authentication;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

	private final MemberService memberService;

	// Handles GET request for /members/login to show the login form
	@GetMapping(value = "/login")
	public String loginForm() {
		return "loginForm"; // Returns the loginForm.html template
	}

	// Handles GET request for /members/join to show the registration form
	@GetMapping(value = "/join")
	public String joinForm(Model model) {
		model.addAttribute("memberDto", new MemberDto());
		return "memberForm"; // Returns the memberForm.html template
	}

	// Handles POST request for /members/join to process registration
	@PostMapping(value = "/join")
	public String newMember(@Valid MemberDto memberDto, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "memberForm"; // If validation errors, return to form
		}
		try {
			memberService.join(memberDto);
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage()); // Add error message to model
			return "memberForm"; // If duplicate email, return to form with error
		}
		return "redirect:/members/login"; // Redirect to login page on successful registration
	}

	@GetMapping("/info")
	public String memberInfo(Model model, Authentication authentication) {
		String email = authentication.getName();
		ResponseMemberDto memberDto = memberService.findMemberByEmail(email);
		model.addAttribute("member", memberDto);
		return "loginSuccess";
	}

}
