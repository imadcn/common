package com.imadcn.framework.common.secret;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * SHA1签名工具类
 * @author Hinsteny
 * @version $ID: MD5Utils 2018-04-27 08:52 All rights reserved.$
 */
public class SHAUtils {

    private static final String SHA1 = "SHA1";

    /**
     * 对文本进行512分组计算最终生成40位的消息摘要
     *
     * @param text
     * @return 加密后的内容
     */
    public static String calculateSha1Val(String text) throws NoSuchAlgorithmException {
        MessageDigest sha1Encoder = MessageDigest.getInstance(SHA1);
        sha1Encoder.update(text.getBytes());
        byte[] encodedBytes = sha1Encoder.digest();
        StringBuilder resultBuffer = new StringBuilder();
        for (byte perByte : encodedBytes) {
            String hex = Integer.toHexString(perByte & 0xff);
            resultBuffer.append((hex.length() > 1 ? "" : "0").concat(hex));
        }
        return resultBuffer.toString();
    }

    /**
     * Test
     * @param args
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String md5PrivateKey = UUID.randomUUID().toString();
        String data = "走遍世界的心不能停...O(∩_∩)O哈哈~";
        String sign = SHAUtils.calculateSha1Val(data + md5PrivateKey);
        System.err.println(String.format("Data: %s, calculate Sha1 to: %s", data + md5PrivateKey, sign));
        String wrongSign = SHAUtils.calculateSha1Val(data + UUID.randomUUID().toString());
        assert sign.equals(wrongSign) : "the sign is not correct";
    }
}
