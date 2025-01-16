package com.nayoung.dataencryptiontest.api;

import com.nayoung.dataencryptiontest.api.dto.AccountDto;
import com.nayoung.dataencryptiontest.domain.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AccountController {

	private final AccountService accountService;

	@PostMapping("/data-encryption-valid")
	public ResponseEntity<?> test(@RequestBody AccountDto request) {
		AccountDto response = accountService.isValidRequest(request);
		return ResponseEntity.ok(response);
	}
}
