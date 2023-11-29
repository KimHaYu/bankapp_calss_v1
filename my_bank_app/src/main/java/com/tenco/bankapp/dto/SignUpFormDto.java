package com.tenco.bankapp.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class SignUpFormDto {

	private String username;
	private String password;
	private String fullname;
	private MultipartFile file; // name 속성과 일치 시켜야 함
	private String originFileName;
	private String uploadFileName;
}
