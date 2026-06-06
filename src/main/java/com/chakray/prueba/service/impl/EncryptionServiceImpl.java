package com.chakray.prueba.service.impl;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import com.chakray.prueba.service.EncryptionService;

import java.util.Base64;

@Slf4j
@Service(value = "encryptionService")
public class EncryptionServiceImpl implements EncryptionService {

    private static final String SECRET_KEY = "12345678901234567890123456789012";

    @Override
    public String encrypt(String password) {

        try {
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");

            Cipher cipher = Cipher.getInstance("AES");

            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] encryptedBytes = cipher.doFinal(password.getBytes());

            return Base64.getEncoder()
                    .encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting password", e);
        }
    }

}
