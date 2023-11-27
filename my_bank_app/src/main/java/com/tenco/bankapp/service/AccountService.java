package com.tenco.bankapp.service;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenco.bankapp.dto.SaveFormDto;
import com.tenco.bankapp.dto.WithdrawFormDto;
import com.tenco.bankapp.handler.exception.CustomRestfullException;
import com.tenco.bankapp.repository.entity.Account;
import com.tenco.bankapp.repository.entity.History;
import com.tenco.bankapp.repository.interfaces.AccountRepository;
import com.tenco.bankapp.repository.interfaces.HistoryRepository;

@Service // IoC 대상 + 싱글톤 관리
public class AccountService {
	@Autowired
	private HistoryRepository historyRepository;
	@Autowired
	private AccountRepository accountRepository;

	/**
	 * 계좌 생성 가능
	 * 
	 * @param dto
	 * @param principalId
	 */
	@Transactional
	public void createdAccount(SaveFormDto dto, Integer principalId) {

		// 계좌 중복 여부 확인
		Account account = Account.builder()
				.number(dto.getNumber())
				.password(dto.getPassword())
				.balance(dto.getBalance())
				.userId(principalId)
				.build();
		
		int resultRowCount = accountRepository.insert(account);
		if (resultRowCount != 1) {
			throw new CustomRestfullException("계좌 생성 실패", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	// 계좌 목록 보기 기능
	public List<Account> readAccountList(Integer userId) {
	List<Account> list = accountRepository.findByUserId(userId);
	return list;
	}

	// 출금 기능 로직 고민해보기
	// 1. 계좌 존재 여부 확인 -> select
	// 2. 본인 계좌 여부 확인 -> select
	// 3. 계좌 비번 확인
    // 4. 잔액 여부 확인
	// 5. 출금 처리 --> update 
	// 6. 거래 내역 등록 --> insert
	// 7. 트랜잭션 처리
	
	@Transactional
	public void updateAccountWithdraw(WithdrawFormDto dto, Integer principalId) {
		
		Account accountEntity = accountRepository
				.findByNumber(dto.getWAccountNumber());
		// 1
		if(accountEntity == null) {
			throw new CustomRestfullException("해당 계좌가 없습니다", HttpStatus.BAD_REQUEST);
		}
		// 2
		if(accountEntity.getUserId() != principalId) {
			throw new CustomRestfullException("본인 소유 계좌가 아닙니다", HttpStatus.UNAUTHORIZED);
		}
		// 3
		if (accountEntity.getPassword().equals(dto.getPassword()) == false) {
			throw new CustomRestfullException("출금 계좌 비밀번호가 틀렸습니다", HttpStatus.BAD_REQUEST);
		}
		// 4
		if (accountEntity.getBalance() < dto.getAmount()) {
			throw new CustomRestfullException("계좌 잔액이 부족합니다", HttpStatus.BAD_REQUEST);
		}
		// 5(객체 모델 상태값 변경 처리)
		accountEntity.withdraw(dto.getAmount());
		accountRepository.updateById(accountEntity);
		
		// 6 거래 내역 등록
		History history = new History();
		history.setAmount(dto.getAmount());
		// 출금 거래 내역에서는 사용자가 출금 후에 잔액을 입력합니다.
		history.setWBalance(accountEntity.getBalance());
		history.setDBalance(null);
		history.setWAccountId(accountEntity.getId());
		history.setDAccountId(null);
		int resultRowCount = historyRepository.insert(history);
		if(resultRowCount != 1) {
			throw new CustomRestfullException("정상 처리 되지 않았습니다", HttpStatus.BAD_REQUEST); 
		}
		
	}
}
