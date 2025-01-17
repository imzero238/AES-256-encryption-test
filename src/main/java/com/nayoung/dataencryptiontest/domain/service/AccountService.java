package com.nayoung.dataencryptiontest.domain.service;

import com.nayoung.dataencryptiontest.api.dto.AccountDto;
import com.nayoung.dataencryptiontest.domain.Account;
import com.nayoung.dataencryptiontest.domain.repository.AccountRepository;
import com.nayoung.dataencryptiontest.aes.AESEncryptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

	private final AccountRepository accountRepository;
	private final AESEncryptionService aesEncryptionService;

	public AccountDto isValidRequest(AccountDto request) {
		Account account = accountRepository.findById(request.id())
			.orElseThrow(() -> new RuntimeException("Account not found"));

		String passwordValidationResult = isValidPassword(account.getPassword(), request.password());
		if(passwordValidationResult.equals("Password is incorrect")) {
			return AccountDto.builder()
				.id(request.id())
				.password("Password is incorrect")
				.accountNumber("No validity check was performed on the account number")
				.build();
		}

		String accountNumberValidationResult = isValidAccountNumber(account.getAccountNumber(), request.accountNumber());
		if(accountNumberValidationResult.equals("Account number is incorrect")) {
			return AccountDto.builder()
				.id(request.id())
				.password(passwordValidationResult)
				.accountNumber("Account number is incorrect")
				.build();
		}

		return AccountDto.builder()
			.id(request.id())
			.password(passwordValidationResult)
			.accountNumber(accountNumberValidationResult)
			.build();
	}

	private String isValidPassword(String password, String encryptedPassword) {
		try {
			String decryptedData = aesEncryptionService.decrypt(encryptedPassword);
			log.info("[AES decryption(password)] {} -> {}", encryptedPassword, decryptedData);
			log.info("Password comparison result: {}", password.equals(decryptedData));
			if (password.equals(decryptedData)) {
				return password;
			}
		} catch (Exception e) {
			log.error("Error decrypting password: {}", e.getMessage());
		}
		return "Password is incorrect";
	}

	private String isValidAccountNumber(String accountNumber, String encryptedAccountNumber) {
		try {
			String decryptedData = aesEncryptionService.decrypt(encryptedAccountNumber);
			log.info("[AES decryption(account number)] {} -> {}", encryptedAccountNumber, decryptedData);
			log.info("Account number comparison result: {}", accountNumber.equals(decryptedData));
			if (accountNumber.equals(decryptedData)) {
				return accountNumber;
			}
		} catch (Exception e) {
			log.error("Error decrypting account number: {}", e.getMessage());
		}
		return "Account number is incorrect";
	}
}
