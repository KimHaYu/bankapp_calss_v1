package com.tenco.bankapp.dto;

import javax.management.loading.PrivateClassLoader;

import lombok.Data;

@Data
public class DepositFormDto {
   private Long amount;
   private String dAccountNumber;
}