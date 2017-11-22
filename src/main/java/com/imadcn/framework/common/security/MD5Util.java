package com.imadcn.framework.common.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5加密
 */
public class MD5Util {

	private static final String CHARSET = "UTF-8";

	private static final String ALG = "MD5";

	public static String encrypt(byte[] bytes) {
		return encrypt(bytes, false);
	}

	public static String encrypt(String str) {
		return encrypt(str, false);
	}

	public static String encryptWithKey(String str, String key) {
		return encryptWithKey(str, key, false);
	}

	public static String encrypt(byte[] bytes, boolean base64) {
		byte[] digest = null;

		try {
			digest = DigestPass.digest(ALG, bytes);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}

		if (base64) {
			return EncodeUtil.base64Encode(digest);
		} else {
			return EncodeUtil.encode(digest);
		}
	}

	public static String encrypt(String str, boolean base64) {
		byte[] digest = null;

		try {
			digest = DigestPass.digest(ALG, str.getBytes(CHARSET));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

		if (base64) {
			return EncodeUtil.base64Encode(digest);
		} else {
			return EncodeUtil.encode(digest);
		}
	}

	public static String encryptWithKey(String str, String key, boolean base64) {
		byte[] digest = null;

		try {
			digest = DigestPass.digest(ALG, str.getBytes(CHARSET), key.getBytes(CHARSET));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

		if (base64) {
			return EncodeUtil.base64Encode(digest);
		} else {
			return EncodeUtil.encode(digest);
		}
	}
}

class DigestPass {
	/**
	 * 
	 * @param alg String 算法，通常为MD5,SHA
	 * @param plainByte byte[] 明文的二进制
	 * @return byte[] 二进制的消息摘要
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] digest(String alg, byte[] plainByte) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(alg);
		md.update(plainByte);
		return md.digest();
	}

	public static byte[] digest(String alg, byte[] plainByte, byte[] key) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(alg);
		md.update(plainByte);
		return md.digest(key);
	}
}
