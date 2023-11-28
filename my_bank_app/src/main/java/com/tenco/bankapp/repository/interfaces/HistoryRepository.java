package com.tenco.bankapp.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tenco.bankapp.repository.entity.History;
@Mapper
public interface HistoryRepository {

	// 거래내역 등록
	public int insert(History history);
	// 거래내역 조회
	public List<History> findByAccountNumber(String id);
	public int updateById(History history);
	public int deleteById(Integer id);
	public List<History> findAll();
	// 동적 쿼리 생성
	// 입금 / 출금 / 전체
	// 반드시 두개 이상의 파라미터 사용시 @Param 을 사용해야 한다.
	public List<History> findByIdAndDynamicType(@Param("type")String type, @Param("accountId")Integer accountId);
}
