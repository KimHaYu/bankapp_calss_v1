package com.tenco.bankapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// 실행의 제어권을 가지고 있으면 라이브러리
// 실행의 제어권을 가지고 있지 않으면 프레임워크
@Controller // IoC에 대상()
@RequestMapping("/temp")
public class TestController {
	// 주소설계
	@GetMapping("/temp")
	public String temp() {
		return "temp";
	}

	@GetMapping("/main-page")
	public String tempMainPage() {
		return "main";
	}
	
	
}
