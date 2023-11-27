package com.tenco.bankapp.repository.entity;

import java.sql.Timestamp;

import org.apache.taglibs.standard.lang.jstl.test.beans.PublicBean1;
import org.eclipse.jdt.internal.compiler.ast.ThisReference;
import org.springframework.http.HttpStatus;

import com.tenco.bankapp.handler.exception.CustomRestfullException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
	private Integer id;
	private String number;
	private String password;
	private Long balance;
	private Integer userId;
	private Timestamp createdAT;

	// 출금 기능
	public void withdraw(Long amount) {
		this.balance -= amount;
	}

	// 입급 기능
	public void deposit(Long amount) {
		this.balance += amount;
	}

	public void checkPassword(String password) {
	  if (this.password.equals(password) == false) {
		throw new CustomRestfullException("계좌 비밀번호가 틀렸습니다", HttpStatus.BAD_REQUEST);
	}
  }

	public void checkBalance(Long amount) {
		  if (this.balance < amount) {
			throw new CustomRestfullException("출금 잔액이 부족 합니다", HttpStatus.BAD_REQUEST);
		}
	  }

	public void checkOwnner(Integer principalId) {
	if (this.userId != principalId) {
		throw new CustomRestfullException("본인 계좌가 아닙니다", HttpStatus.BAD_REQUEST);
	}	  
  }
	// TODO - 추후 추가
	// 패스워드 체크 기능
	// 잔액 여부 확인 기능
	// 계좌 소유자 확인 기능
}
