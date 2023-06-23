package com.steven.stevenadmin.accountservice.utils;


import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

/*
    A jwt key generator utility class
 */
public class KeyGeneratorUtil {

    public static KeyPair generateRsaKey() throws NoSuchAlgorithmException {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            throw new IllegalStateException();
        }
        return keyPair;
    }
}
