package com.tenco.bankapp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenco.bankapp.dto.SignUpFormDto;
import com.tenco.bankapp.handler.exception.CustomRestfullException;
import com.tenco.bankapp.repository.entity.User;
import com.tenco.bankapp.service.UserService;
import com.tenco.bankapp.utils.Define;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired // DI 처리(의존성 주입)
	private UserService userService;
	@Autowired
	private HttpSession session;

	// public UserController(UserService userService) {
	// this.userService = userService ;
	// }
	// 회원 가입 페이지 요청
	// http://localhost:80/user/sign-up
	@GetMapping("/sign-up")
	public String signUp() {
		return "user/signUp";
	}

	// 로그인 가입 페이지 요청
	// http://localhost:80/user/sign-in
	@GetMapping("/sign-in")
	public String signin() {
		return "user/signIn";
	}

	/*
	 * 회원 가입 처리
	 * 
	 * @param dto
	 * 
	 * @return 리다이렉트 로그인 페이지
	 */
	@PostMapping("/sign-up")
	public String signUpProc(SignUpFormDto dto) {
		// 1. 유효성 검사
		if (dto.getUsername() == null || dto.getUsername().isEmpty()) {
			throw new CustomRestfullException("username을 입력하세요", HttpStatus.BAD_REQUEST);
		}
		if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new CustomRestfullException("password을 입력하세요", HttpStatus.BAD_REQUEST);
		}
		if (dto.getFullname() == null || dto.getFullname().isEmpty()) {
			throw new CustomRestfullException("fullname을 입력하세요", HttpStatus.BAD_REQUEST);
		}

		int resultRowCount = userService.signUp(dto);
		if (resultRowCount != 1) {
			// 다른처리
		}
		return "redirect:/user/sign-in";
	}

	@PostMapping("/sign-in")
	public String signInProc(SignUpFormDto dto) {
		// 1. 유효성 검사
		if (dto.getUsername() == null || dto.getUsername().isEmpty()) {
			throw new CustomRestfullException("username을 입력하시오", HttpStatus.BAD_REQUEST);
		}
		if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new CustomRestfullException("password을 입력하시오", HttpStatus.BAD_REQUEST);
		}
		// 서비스 호출
		User principal = userService.signIn(dto);
		session.setAttribute(Define.PRINCIPAL, principal); // 세션에 저장
	
		return "redirect:/account/list";
         
	}
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/user/sign-in";
	}
}
