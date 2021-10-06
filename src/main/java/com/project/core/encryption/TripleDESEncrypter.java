package com.project.core.encryption;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.commons.codec.binary.Hex;
import sun.misc.BASE64Decoder;

import javax.crypto.*;
import javax.crypto.spec.DESedeKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

public class TripleDESEncrypter {
    public TripleDESEncrypter() {
    }

    public static SecretKey generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keygen = KeyGenerator.getInstance("DESede");
        return keygen.generateKey();
    }

    public static void writeKey(SecretKey key, File f) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DESede");
        DESedeKeySpec keyspec = (DESedeKeySpec) keyfactory.getKeySpec(key, DESedeKeySpec.class);
        byte[] rawkey = keyspec.getKey();
        FileOutputStream out = new FileOutputStream(f);
        out.write(rawkey);
        out.close();
    }

    public static SecretKey readKey(File f) throws IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
        DataInputStream in = new DataInputStream(new FileInputStream(f));
        byte[] rawkey = new byte[(int) f.length()];
        in.readFully(rawkey);
        in.close();
        DESedeKeySpec keyspec = new DESedeKeySpec(rawkey);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DESede");
        SecretKey key = keyfactory.generateSecret(keyspec);
        return key;
    }

    public static SecretKey readKey(byte[] rawkey) throws Exception {
        try {
            DESedeKeySpec keyspec = new DESedeKeySpec(rawkey);
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("TripleDES");
            SecretKey key = keyfactory.generateSecret(keyspec);
            return key;
        } catch (Exception var4) {
            throw new Exception(var4);
        }
    }

    public static void encrypt(SecretKey key, InputStream in, OutputStream out) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IOException {
        Cipher cipher = Cipher.getInstance("DESede");
        cipher.init(1, key);
        CipherOutputStream cos = new CipherOutputStream(out, cipher);
        byte[] buffer = new byte[2048];

        int bytesRead;
        while ((bytesRead = in.read(buffer)) != -1) {
            cos.write(buffer, 0, bytesRead);
        }

        cos.close();
        Arrays.fill(buffer, (byte) 0);
    }

    public static void decrypt(SecretKey key, InputStream in, OutputStream out) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, IOException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("DESede");
        cipher.init(2, key);
        byte[] buffer = new byte[2048];

        int bytesRead;
        while ((bytesRead = in.read(buffer)) != -1) {
            out.write(cipher.update(buffer, 0, bytesRead));
        }

        out.write(cipher.doFinal());
        out.flush();
    }

    public static String encrypt(SecretKey secretKey, String in) throws Exception {
        Cipher cipher = Cipher.getInstance("DESede");
        cipher.init(1, secretKey);
        return Base64.encode(cipher.doFinal(in.getBytes()));
    }

    public static String decrypt(SecretKey secretKey, String in) throws Exception {
        Cipher cipher = Cipher.getInstance("DESede");
        cipher.init(2, secretKey);
        byte[] dec = (new BASE64Decoder()).decodeBuffer(in);
        byte[] utf8 = cipher.doFinal(dec);
        return new String(utf8, "UTF8");
    }

    public static byte[] decryptNoPadding(SecretKey key, byte[] obj) throws NoSuchAlgorithmException, InvalidKeyException, IOException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
        cipher.init(2, key);
        return cipher.doFinal(obj);
    }

    public static String encryptNoPadding(SecretKey key, byte[] in) throws Exception {
        byte[] encrypted = new byte[0];

        try {
            Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
            cipher.init(1, key);
            encrypted = new byte[0];
            encrypted = cipher.doFinal(in);
        } catch (Exception var4) {
            throw new Exception(var4);
        }

        return new String(Hex.encodeHex(encrypted));
    }

    public static void main(String[] args) {
    }
}
