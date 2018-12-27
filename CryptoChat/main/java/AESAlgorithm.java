package com.example.thann.cryptochat;

import android.util.Base64;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by thann on 11/21/2017.
 */

public class AESAlgorithm {

    public static String algo = "RSA";
    public byte[] keyValue;

    public AESAlgorithm(byte key[]) {
        keyValue = key;
    }

    public Key generateKey() throws Exception {
        Key key = new SecretKeySpec(keyValue, algo);
        return key;
    }

    public String encrypt(String msg) throws Exception{
        Key key = generateKey();
        Cipher c = Cipher.getInstance("AES");
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(msg.getBytes());
        String encryptedValue = Base64.encodeToString(encVal, Base64.DEFAULT);
        return encryptedValue;
    }

    public String decrypt(String msg) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(algo);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedValue = Base64.decode(msg, Base64.DEFAULT);
       // byte[] decodedValue = new Base64Decoder().decodeBuffer(msg);
        byte[] decValue = c.doFinal(decodedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;

    }
}
