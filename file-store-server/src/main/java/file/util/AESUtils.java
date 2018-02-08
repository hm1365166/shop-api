package file.util;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;

/**
 * AES
 * @author:HM
 * @date: 2017/9/30 16:11:39
 *
 */
public class AESUtils {
    private static final String AES_KEY = "D181783B876B4A3C9684BFBBB246810A";
    private static final String AES_IV = "68A847E5FB2D48CB85496FFA5A73E4B3";

    public static String aesEncrypt(String source) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        SecretKeySpec keySpec = new SecretKeySpec(messageDigest.digest(AES_KEY.getBytes("UTF-8")), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(messageDigest.digest(AES_IV.getBytes("UTF-8"))));
        byte[] encryptByte = cipher.doFinal(source.getBytes("UTF-8"));
        return new String(Hex.encodeHex(encryptByte));
    }

    public static String aesDecrypt(String base64Str) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        SecretKeySpec keySpec = new SecretKeySpec(messageDigest.digest(AES_KEY.getBytes("UTF-8")), "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(messageDigest.digest(AES_IV.getBytes("UTF-8"))));
        byte[] decryptContent = cipher.doFinal(Hex.decodeHex(base64Str.toCharArray()));
        return new String(decryptContent, "UTF-8");
    }

    public static void main(String[] args) {
        try {
            String enc = aesEncrypt("6");
            System.out.println(enc);
            System.out.println(aesDecrypt(enc));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
