package com.project.core.encryption;

import javax.crypto.SecretKey;

public class UserManagmentEncrypter extends TripleDESEncrypter {
    public UserManagmentEncrypter() {
    }

    public static String encrypt(String in) throws Exception {
        SecretKey secretKey = getSecretKey();
        return encrypt(secretKey, in);
    }

    public static String decrypt(String in) throws Exception {
        SecretKey secretKey = getSecretKey();
        return decrypt(secretKey, in);
    }

    private static SecretKey getSecretKey() throws Exception {
        byte[] key1 = "BPMellatBPMellatBPMellat".getBytes();
        return readKey(key1);
    }
}
