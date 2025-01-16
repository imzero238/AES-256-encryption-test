package com.nayoung.dataencryptiontest.api.dto;

import lombok.Builder;

@Builder
public record AccountDto(
	Long id,
	String password,
	String accountNumber
) {
}
