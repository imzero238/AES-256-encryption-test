package com.nayoung.dataencryptiontest.dataencryption;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@Slf4j
public class DataEncryptionService {

	public static final String TEST_ACCOUNT_NUMBER = "584132-50232493";

	public boolean isAccountNumberValid(DataEncryptionType type, String encryptedAccountNumber) {
		log.info("[{}] -> {}", type, encryptedAccountNumber);

		if(type == DataEncryptionType.BASE64) {
			try {
				return TEST_ACCOUNT_NUMBER.equals(decodeBase64(encryptedAccountNumber));
			} catch (IllegalArgumentException e) {
				log.error("Error decoding Base64 account number: {}", e.getMessage());
			}
		}
		return false;
	}

	private String decodeBase64(String encryptedAccountNumber) {
		return new String(Base64.getDecoder().decode(encryptedAccountNumber));
	}
}
