package com.tenco.bankapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenco.bankapp.dto.SignUpFormDto;
import com.tenco.bankapp.handler.exception.CustomRestfullException;
import com.tenco.bankapp.repository.entity.User;
import com.tenco.bankapp.repository.interfaces.UserRepository;

@Service
public class UserService {

	@Autowired // 의존주입 (생성자, 메서드)
	private UserRepository userRepository;

	@Transactional
	public int signUp(SignUpFormDto dto) {
  
		// username 에 중복 여부 확인 생략
		
		User user = User.builder().username(dto.getUsername()).password(dto.getPassword()).fullname(dto.getPassword())
				.build(); // build() 반드시 호출

		int ressultRowCount = userRepository.insert(user);
		if(ressultRowCount !=1) {
			throw new CustomRestfullException("회원 가입 실패",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ressultRowCount;
	}

	public User signIn(SignUpFormDto dto) {
		
		User userEntity =userRepository.findUserByUsernameAndPassword(dto);
		if (userEntity == null) {
			throw new CustomRestfullException("아이디 혹은 비번이 틀렸습니다.", HttpStatus.BAD_REQUEST);
		}
		return userEntity;
	}

}
