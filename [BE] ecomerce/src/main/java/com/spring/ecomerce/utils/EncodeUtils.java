package com.spring.ecomerce.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncodeUtils {
    /**
     * Lower case Hex Digits.
     */
    private static final String HEX_DIGITS = "0123456789abcdef";

    /**
     * Byte mask.
     */
    private static final int BYTE_MSK = 0xFF;

    /**
     * Number of bits per Hex digit (4).
     */
    private static final int HEX_DIGIT_BITS = 4;

    /**
     * Hex digit mask.
     */
    private static final int HEX_DIGIT_MASK = 0xF;

    public static String getPasswordHash(String pw, String type) {

        String hashed=null;

        if(type.isEmpty()) type="SHA1";

        //SHA1の場合
        try{
            if(type.equals("SHA1")) {
                hashed = computeSha1OfByteArray(pw.getBytes("UTF-8"));
            }
            //SHA256の場合
            if(type.equals("SHA256")) {
                hashed = computeSha256OfByteArray(pw.getBytes("UTF-8"));
            }
        }
        catch (Exception exception){
            hashed = "";
        }

        return hashed;
    }

    /**
     * SHA1ハッシュ
     * @param message　パスワードのバイト
     * @return ハッシュ済み文字
     * @throws UnsupportedOperationException
     */
    private static String computeSha1OfByteArray(final byte[] message) throws UnsupportedOperationException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(message);
            byte[] res = md.digest();
            return toHexString(res);
        } catch (NoSuchAlgorithmException ex) {
            throw new UnsupportedOperationException(ex);
        }
    }

    /**
     * SHA256ハッシュ
     * @param message　パスワードのバイト
     * @return ハッシュ済み文字
     * @throws UnsupportedOperationException
     */
    private static String computeSha256OfByteArray(final byte[] message) throws UnsupportedOperationException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(message);
            byte[] res = md.digest();
            return toHexString(res);
        } catch (NoSuchAlgorithmException ex) {
            throw new UnsupportedOperationException(ex);
        }
    }

    /**
     * HEX変換
     * @param byteArray　SHA1にハッシュしたバイトArray
     * @return　HEX文字
     */
    private static String toHexString(final byte[] byteArray) {
        StringBuilder sb = new StringBuilder(byteArray.length * 2);
        for (int i = 0; i < byteArray.length; i++) {
            int b = byteArray[i] & BYTE_MSK;
            sb.append(HEX_DIGITS.charAt(b >>> HEX_DIGIT_BITS)).append(HEX_DIGITS.charAt(b & HEX_DIGIT_MASK));
        }
        return sb.toString();
    }
}
