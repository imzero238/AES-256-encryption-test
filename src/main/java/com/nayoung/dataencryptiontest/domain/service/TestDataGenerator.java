package com.nayoung.dataencryptiontest.domain.service;

import com.nayoung.dataencryptiontest.domain.Account;
import com.nayoung.dataencryptiontest.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestDataGenerator {

	private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+[]{}|;:,.<>?";

	private final AccountRepository accountRepository;

	@EventListener(ApplicationReadyEvent.class)
	public void createTestAccounts() {
		try {
			for(int i = 0; i < 10; i++) {
				Account account = Account.builder()
					.accountNumber(RandomStringUtils.randomNumeric(7) + "-" + RandomStringUtils.randomNumeric(9))
					.password(createTestPassword())
					.build();

				accountRepository.save(account);
			}
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
		}
	}

	private String createTestPassword() {
		String randomPassword = RandomStringUtils.randomAlphanumeric(15);
		randomPassword = randomPassword + SPECIAL_CHARACTERS.charAt(new SecureRandom().nextInt(SPECIAL_CHARACTERS.length()));

		List<Character> characters = new ArrayList<>();
		for (char c : randomPassword.toCharArray()) {
			characters.add(c);
		}

		Collections.shuffle(characters);

		StringBuilder shuffledString = new StringBuilder();
		for (char c : characters) {
			shuffledString.append(c);
		}
		return shuffledString.toString();
	}
}
