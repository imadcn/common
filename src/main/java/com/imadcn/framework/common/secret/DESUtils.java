package com.imadcn.framework.common.secret;

import com.imadcn.framework.common.string.ByteUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * DES加解密算法工具
 * 加密用的Key 可以用若干个字母和数字组成，最好不要用保留字符
 * DES: DES-56-CBC加密模式，key需要为8位。
 * 3DES: DESede-168-CBC加密模式，key需要为24位。
 *
 * @author Hinsteny
 * @version $ID: DESUtils 2018-04-15 14:49 All rights reserved.$
 */
public class DESUtils {

    /**
     * 编码类型
     */
    private static final String CHARCODE = "UTF-8";

    /**
     * 默认的DES秘钥长度
     **/
    private static final int DEFAULT_DES_KEY_LENGTH = 56;

    /**
     * 默认的3DES秘钥长度
     **/
    private static final int DEFAULT_3DES_KEY_LENGTH = 168;

    /**
     * 使用CBC模式，需要一个向量iv，可增加加密算法的强度
     */
    private static final String MODEL = "12345678";

    /**
     * 算法
     */
    private static final String[] algorithms = {"DES", "DESede"};

    /**
     * [DES] 算法/模式/补码方式
     */
    private static final String ALGORITHMDES = "DES/CBC/PKCS5Padding";

    /**
     * [3DES] 算法/模式/补码方式
     */
    private static final String ALGORITHM3DES = "DESede/CBC/PKCS5Padding";

    private static final Map<String, String> ALGORITHMS_MAP;

    static {
        Map<String, String> data = new HashMap<>();
        data.put(algorithms[0], ALGORITHMDES);
        data.put(algorithms[1], ALGORITHM3DES);

        ALGORITHMS_MAP = Collections.unmodifiableMap(data);
    }

    /**
     * 生成一个AES加密私钥串
     *
     * @return
     */
    public static String generateDESKey() {
        return generateDESKey(algorithms[0], DEFAULT_DES_KEY_LENGTH);
    }

    /**
     * 生成一个AES加密私钥串
     *
     * @return
     */
    public static String generate3DESKey() {
        return generateDESKey(algorithms[1], DEFAULT_3DES_KEY_LENGTH);
    }

    /**
     * @param keyLen AES秘钥长度可选值有
     * @return
     */
    private static String generateDESKey(String algorithm, int keyLen) {
        if (!(DEFAULT_DES_KEY_LENGTH == keyLen || DEFAULT_3DES_KEY_LENGTH == keyLen)) {
            throw new RuntimeException("DES key length is not correct");
        }
        KeyGenerator kg = null;
        try {
            kg = KeyGenerator.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        kg.init(keyLen);
        SecretKey sk = kg.generateKey();
        byte[] b = sk.getEncoded();
        return ByteUtils.byteToHexStr(b);
    }

    /**
     * 判断key长度有效性
     *
     * @param needKeyLen
     * @return
     */
    private static boolean judgeKey(String key, int needKeyLen) {
        if (key == null || "".equals(key.trim())) {
            throw new RuntimeException("DES key is not valid");
        }
        if (!(needKeyLen == key.length())) {
            throw new RuntimeException("DES key length is not correct");
        }
        return true;
    }

    /**
     * 加密
     *
     * @param src 需要加密的内容
     * @param key 加密秘钥
     * @return
     */
    public static String encrypt(String src, String key) throws Exception {
        judgeKey(key, 8);
        return encrypt(src, key, algorithms[0], CHARCODE);
    }

    /**
     * 加密
     *
     * @param src 需要加密的内容
     * @param key 加密秘钥
     * @return
     */
    public static String encrypt3DES(String src, String key) throws Exception {
        judgeKey(key, 24);
        return encrypt(src, key, algorithms[1], CHARCODE);
    }

    /**
     * 解密
     *
     * @param src 密文
     * @param key 密钥
     * @return
     */
    public static String decrypt(String src, String key) throws Exception {
        judgeKey(key, 8);
        return decrypt(src, key, algorithms[0], CHARCODE);
    }

    /**
     * 解密
     *
     * @param src 密文
     * @param key 密钥
     * @return
     */
    public static String decrypt3DES(String src, String key) throws Exception {
        judgeKey(key, 24);
        return decrypt(src, key, algorithms[1], CHARCODE);
    }

    /**
     * 加密
     *
     * @param src       需要加密的内容
     * @param key       加密秘钥
     * @param algorithm 加密所用算法
     * @param charset   编码
     * @return
     */
    private static String encrypt(String src, String key, String algorithm, String charset) throws Exception {
        String afterCode;
        byte[] raw = key.getBytes();
        Cipher cipher = Cipher.getInstance(ALGORITHMS_MAP.get(algorithm));
        SecretKeySpec skeySpec = new SecretKeySpec(raw, algorithm);
        IvParameterSpec iv = new IvParameterSpec(MODEL.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(src.getBytes(charset));
        //此处使用BASE64做转码功能，防止中文乱码
        afterCode = Base64Utils.base64Encodes(encrypted);
        return afterCode.replaceAll("\r|\n", "");
    }

    /**
     * 解密
     *
     * @param src 密文
     * @param key 密钥
     * @return
     */
    private static String decrypt(String src, String key, String algorithm, String charset) throws Exception {
        String originalString;
        byte[] raw = key.getBytes(CHARCODE);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, algorithm);
        Cipher cipher = Cipher.getInstance(ALGORITHMS_MAP.get(algorithm));
        IvParameterSpec iv = new IvParameterSpec(MODEL.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        //先用base64解密
        byte[] encrypted1 = Base64Utils.base64Decode(src.getBytes(charset));
        byte[] original = cipher.doFinal(encrypted1);
        originalString = new String(original, charset);
        return originalString;
    }

    /**
     * test
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        String data = "走遍世界的心不能停...O(∩_∩)O哈哈~";
        String key, secret;
        key = DESUtils.generateDESKey();
        System.out.println("key is : " + key);
        secret = DESUtils.encrypt(data, key);
        System.out.println(String.format("encrypt data: %s, result: %s", data, secret));
        String dData = DESUtils.decrypt(secret, key);
        System.out.println("decrypt result: " + dData);
        key = DESUtils.generate3DESKey();
        System.out.println("key is : " + key);
        secret = DESUtils.encrypt3DES(data, key);
        System.out.println(String.format("encrypt data: %s, result: %s", data, secret));
        dData = DESUtils.decrypt3DES(secret, key);
        System.out.println("decrypt result: " + dData);

    }
}
