package com.imadcn.framework.common.secret;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * SHA 相关签名工具类
 * @author Hinsteny
 * @version $ID: MD5Utils 2018-04-27 08:52 All rights reserved.$
 */
public class SHAUtils {

    /**
     * 签名算法
     */
    public enum SignType {

        SHA1("SHA1"),
        SHA224("SHA-224"),
        SHA256("SHA-256"),
        SHA384("SHA-384"),
        SHA512("SHA-512"),
        ;

        private String algorithm;

        SignType(String algorithm) {
            this.algorithm = algorithm;
        }

        public String getAlgorithm() {
            return algorithm;
        }
    }

    /**
     * 对文本进行512分组计算最终生成40位的消息摘要
     *
     * @param content
     * @return 加密后的内容
     */
    public static String calculateSha1Val(String content) throws NoSuchAlgorithmException {
        return calculateSignVal(SignType.SHA1, content);
    }

    /**
     * 对文本进行512分组计算最终生成56位的消息摘要
     *
     * @param content
     * @return 加密后的内容
     */
    public static String calculateSha224Val(String content) throws NoSuchAlgorithmException {
        return calculateSignVal(SignType.SHA224, content);
    }

    /**
     * 对文本进行512分组计算最终生成64位的消息摘要
     *
     * @param content
     * @return 加密后的内容
     */
    public static String calculateSha256Val(String content) throws NoSuchAlgorithmException {
        return calculateSignVal(SignType.SHA256, content);
    }

    /**
     * 对文本进行512分组计算最终生成96位的消息摘要
     *
     * @param content
     * @return 加密后的内容
     */
    public static String calculateSha384Val(String content) throws NoSuchAlgorithmException {
        return calculateSignVal(SignType.SHA384, content);
    }

    /**
     * 对文本进行512分组计算最终生成128位的消息摘要
     *
     * @param content
     * @return 加密后的内容
     */
    public static String calculateSha512Val(String content) throws NoSuchAlgorithmException {
        return calculateSignVal(SignType.SHA512, content);
    }

    /**
     * 根据指定的签名算法, 对输入内容进行签名
     * @param type
     * @param content
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static String calculateSignVal (SignType type, String content) throws NoSuchAlgorithmException {
        MessageDigest sha1Encoder = MessageDigest.getInstance(type.getAlgorithm());
        sha1Encoder.update(content.getBytes());
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
        String key = UUID.randomUUID().toString();
        String data = "走遍世界的心不能停...O(∩_∩)O哈哈~", sign, wrongSign;
        sign = SHAUtils.calculateSha1Val(data + key);
        System.err.println(String.format("Data: %s, calculate Sha1 to: %s", data + key, sign));
        wrongSign = SHAUtils.calculateSha1Val(data + UUID.randomUUID().toString());
        assert sign.equals(wrongSign) : "the sign is not correct";

        sign = SHAUtils.calculateSha224Val(data + key);
        System.err.println(String.format("Data: %s, calculate Sha224 to: %s", data + key, sign));
        wrongSign = SHAUtils.calculateSha224Val(data + UUID.randomUUID().toString());
        assert sign.equals(wrongSign) : "the sign is not correct";

        sign = SHAUtils.calculateSha256Val(data + key);
        System.err.println(String.format("Data: %s, calculate Sha256 to: %s", data + key, sign));
        wrongSign = SHAUtils.calculateSha256Val(data + UUID.randomUUID().toString());
        assert sign.equals(wrongSign) : "the sign is not correct";

        sign = SHAUtils.calculateSha384Val(data + key);
        System.err.println(String.format("Data: %s, calculate Sha384 to: %s", data + key, sign));
        wrongSign = SHAUtils.calculateSha384Val(data + UUID.randomUUID().toString());
        assert sign.equals(wrongSign) : "the sign is not correct";

        sign = SHAUtils.calculateSha512Val(data + key);
        System.err.println(String.format("Data: %s, calculate Sha512 to: %s", data + key, sign));
        wrongSign = SHAUtils.calculateSha512Val(data + UUID.randomUUID().toString());
        assert sign.equals(wrongSign) : "the sign is not correct";
    }
}
