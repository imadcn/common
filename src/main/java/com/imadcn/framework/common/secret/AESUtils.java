package com.imadcn.framework.common.secret;

import com.imadcn.framework.common.string.ByteUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * AES加解密算法
 * 加密用的Key 可以用26个字母和数字组成，最好不要用保留字符
 * 此处使用AES-128-CBC加密模式，key需要为16位。
 *
 * @author Hinsteny
 * @version $ID: AESUtils 2018-04-15 14:45 All rights reserved.$
 */
public class AESUtils {

    /** 默认的AES秘钥长度 **/
    private static final int DEFAULT_KEY_LENGTH = 128;

    /** 算法 */
    private static final String AES       = "AES";

    /** 算法/模式/补码方式 */
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

    /** 编码类型 */
    private static final String CHARCODE  = "UTF-8";

    /** 使用CBC模式，需要一个向量iv，可增加加密算法的强度 */
    private static final String MODEL     = "0102030405060708";

    /**
     * 生成一个AES加密私钥串
     * @return
     */
    public static String generateAESKey() {
        return generateAESKey(DEFAULT_KEY_LENGTH);
    }

    /**
     *
     * @param keyLen AES秘钥长度可选值有
     * @return
     */
    private static String generateAESKey(int keyLen) {
        if (!(keyLen == 128)) {
            throw new RuntimeException("AES key length is not correct");
        }
        KeyGenerator kg = null;
        try {
            kg = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        kg.init(keyLen);
        SecretKey sk = kg.generateKey();
        byte[] b = sk.getEncoded();
        return byteToHexString(b);
    }

    /**
     * 判断key长度有效性
     * @param keyLen
     * @return
     */
    private static boolean judgeKeyLen(int keyLen) {
        if (!(keyLen == 16)) {
            throw new RuntimeException("AES key length is not correct");
        }
        return true;
    }

    /**
     * byte数组转化为16进制字符串
     * @param bytes
     * @return
     */
    public static String byteToHexString(byte[] bytes){
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            int source = (bytes[i] & 0xFF);
            String strHex=Integer.toHexString(source);
            sb.append(strHex.charAt(0));
        }
        return  sb.toString();
    }

    /**
     * AES加密
     *
     * @param content 需要加密的内容
     * @param key 加密秘钥
     * @return
     */
    public static String encrypt(String content, String key) throws Exception {
        return encryptThenBase64(content, key, CHARCODE);
    }

    /**
     * AES解密
     *
     * @param content 密文
     * @param key 密钥
     * @return
     */
    public static String decrypt(String content, String key) throws Exception {
        return decryptAfterBase64Decode(content, key, CHARCODE);
    }

    /**
     * 对输入的字符串进行AES加密, 然后再做Base64转码,防止中文乱码
     *
     * @param content 需要加密的内容
     * @param key 加密秘钥
     * @param charset 编码
     * @return
     */
    public static String encryptThenBase64(String content, String key, String charset) throws Exception {
        byte[] contentBytes = content.getBytes(charset);
        byte[] tBytes = encryptTBytes(contentBytes, key);
        String secret = Base64Utils.base64Encodes(tBytes);
        return secret.replaceAll("\r|\n", "");
    }

    /**
     * 对输入的字符串进行AES加密, 然后再转为十六进制字符串
     *
     * @param content 需要加密的内容
     * @param key 加密秘钥
     * @param charset 编码
     * @return
     */
    public static String encryptThenHex(String content, String key, String charset) throws Exception {
        byte[] contentBytes = content.getBytes(charset);
        byte[] tBytes = encryptTBytes(contentBytes, key);
        String secret = ByteUtils.byteToHex(tBytes);
        return secret.replaceAll("\r|\n", "");
    }

    /**
     * 对输入的字节数据进行AES加密
     *
     * @param content 需要加密的内容
     * @param key 加密秘钥
     * @return
     */
    public static byte[] encryptTBytes(byte[] content,String key) throws Exception {
        if (key == null || content == null || content.length == 0) {
            throw new IllegalArgumentException();
        }
        judgeKeyLen(key.length());
        byte[] raw = key.getBytes();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
        IvParameterSpec iv = new IvParameterSpec(MODEL.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(content);
        return encrypted;
    }

    /**
     * 先base64解密, 然后再AES解密
     *
     * @param content 密文
     * @param key 密钥
     * @return
     */
    public static String decryptAfterBase64Decode(String content, String key, String charset) throws Exception {
        byte[] decodeBuffer = Base64.getDecoder().decode(content);
        byte[] decryptTBytes = decryptTBytes(decodeBuffer, key);
        String originalString = new String(decryptTBytes, charset);
        return originalString;
    }

    /**
     * 先做hex字节码还原, 然后再AES解密
     *
     * @param content 密文
     * @param key 密钥
     * @return
     */
    public static String decryptAfterHexDecode(String content, String key, String charset) throws Exception {
        byte[] decodeBuffer = ByteUtils.hexTBytes(content);
        byte[] decryptTBytes = decryptTBytes(decodeBuffer, key);
        String originalString = new String(decryptTBytes, charset);
        return originalString;
    }

    /**
     * 解密
     *
     * @param content 密文
     * @param key 密钥
     * @return
     */
    public static byte[] decryptTBytes(byte[] content, String key) throws Exception {
        if (key == null || content == null || content.length == 0) {
            throw new IllegalArgumentException();
        }
        judgeKeyLen(key.length());
        byte[] raw = key.getBytes(CHARCODE);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        IvParameterSpec iv = new IvParameterSpec(MODEL.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        byte[] original = cipher.doFinal(content);
        return original;
    }

    /**
     * test
     * @param args
     */
    public static void main(String[] args) throws Exception {
        String data = "走遍世界的心不能停...O(∩_∩)O哈哈~", key, secret, origin;
        key = AESUtils.generateAESKey(128);
        System.out.println("key length 128: " + key);
        System.out.println("============== 默认AES加解密前后使用Base64加解码 ==============");
        secret = AESUtils.encrypt(data, key);
        System.out.println(String.format("encrypt data: %s, result: %s", data, secret));
        origin = AESUtils.decrypt(secret, key);
        System.out.println("decrypt result: " + origin);
        System.out.println("============== 指定AES加解密前后使用Hex加解码 ==============");
        secret = AESUtils.encryptThenHex(data, key, "UTF-8");
        System.out.println(String.format("encrypt data: %s, result: %s", data, secret));
        origin = AESUtils.decryptAfterHexDecode(secret, key, "UTF-8");
        System.out.println("decrypt result: " + origin);

    }
}
