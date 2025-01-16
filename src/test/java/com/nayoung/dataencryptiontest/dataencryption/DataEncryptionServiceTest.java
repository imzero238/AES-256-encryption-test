package com.nayoung.dataencryptiontest.dataencryption;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Base64;

@SpringBootTest
class DataEncryptionServiceTest {

	@Autowired
	private DataEncryptionService dataEncryptionService;

	@Test
	void BASE64_test() {
		String encryptedAccountNumber = Base64.getEncoder()
			.encodeToString(DataEncryptionService.TEST_ACCOUNT_NUMBER.getBytes());

		Assertions.assertTrue(dataEncryptionService
			.isAccountNumberValid(DataEncryptionType.BASE64, encryptedAccountNumber));
	}
}