package com.practice.springsecondphrasepractice.service;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.util.Base64;

@Component
public class Aes {

    private static final String AES = "AES";
    private static final String AES_ECB = "AES/ECB/PKCS5Padding";
    private static final String AES_CBC = "AES/CBC/PKCS5Padding";
    private static final String iv = "2021121018304400";
    private static final int offset = 16;
    private static final String key = System.getenv("key");
    private static final SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), AES);
    private static final IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8), 0, offset);

    public Aes() {
        Security.addProvider(new BouncyCastleProvider());
    }


    // 加密 : 決定 ECB or CBC ->
    public static String aesECBEncode(String str) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_ECB);
        byte[] strBytes = str.getBytes(StandardCharsets.UTF_8); // 轉成Byte
        cipher.init(Cipher.ENCRYPT_MODE, keySpec); // 加密模式
        byte[] doFinal = cipher.doFinal(strBytes); // 執行加密
        return new String(Base64.getEncoder().encode(doFinal)); // Base64編碼
    }

    public static String aesECBDecode(String str) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_ECB);
        cipher.init(Cipher.DECRYPT_MODE, keySpec); // 解密模式
        byte[] doFinal = cipher.doFinal(Base64.getDecoder().decode(str)); // 以Base64解碼，執行解密
        return new String(doFinal);
    }

    public static String aesCBCEncode(String str) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_CBC);
        byte[] strBytes = str.getBytes(StandardCharsets.UTF_8); // 轉成Byte
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec); // 加密模式
        byte[] doFinal = cipher.doFinal(strBytes); // 執行加密
        return new String(Base64.getEncoder().encode(doFinal)); // Base64編碼
    }

    public static String aesCBCDecode(String str) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_CBC);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec); // 解密模式
        byte[] doFinal = cipher.doFinal(Base64.getDecoder().decode(str)); // 以Base64解碼，執行解密
        return new String(doFinal);
    }
}