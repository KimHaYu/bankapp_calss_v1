package com.tenco.bankapp.repository.entity;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
	private Integer id;
	private String username;
	private String password;
	private String fullname;
	private Timestamp createdAt;

	private String originFileName;
	private String uploadFileName;

	public String setUpUserImage() {

		return uploadFileName == null ? "http://picsum.photos/id/1/350" : "/images/uploads/" + uploadFileName;
	}
}
