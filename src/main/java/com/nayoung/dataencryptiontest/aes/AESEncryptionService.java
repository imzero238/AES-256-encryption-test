package com.nayoung.dataencryptiontest.aes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Service
@RequiredArgsConstructor
@Slf4j
public class AESEncryptionService {

	@Value("${encrypt.secret-key}")
	private String SECRET_KEY;

	public String encrypt(String plainText) throws InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		// Generate random IV
		byte[] iv = new byte[16];
		SecureRandom random = new SecureRandom();
		random.nextBytes(iv);

		// Initialize cipher
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		SecretKeySpec keySpec = new SecretKeySpec(Base64.getDecoder().decode(SECRET_KEY), "AES");
		IvParameterSpec ivParams = new IvParameterSpec(iv);
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParams);

		// Encrypt data
		byte[] encryptedData = cipher.doFinal(plainText.getBytes());

		// Combine IV and encrypted data
		byte[] combined = new byte[iv.length + encryptedData.length];
		System.arraycopy(iv, 0, combined, 0, iv.length);
		System.arraycopy(encryptedData, 0, combined, iv.length, encryptedData.length);

		return Base64.getEncoder().encodeToString(combined);
	}

	public String decrypt(String encryptedText) throws InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		byte[] combined = Base64.getDecoder().decode(encryptedText);

		// Extract IV
		byte[] iv = new byte[16];
		System.arraycopy(combined, 0, iv, 0, iv.length);

		// Extract encrypted data
		byte[] encryptedData = new byte[combined.length - iv.length];
		System.arraycopy(combined, iv.length, encryptedData, 0, encryptedData.length);

		// Initialize cipher for decryption
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		SecretKeySpec keySpec = new SecretKeySpec(Base64.getDecoder().decode(SECRET_KEY), "AES");
		IvParameterSpec ivParams = new IvParameterSpec(iv);
		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParams);

		// Decrypt data
		return new String(cipher.doFinal(encryptedData));
	}
}
