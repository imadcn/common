package com.imadcn.framework.common.security;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.imadcn.framework.common.exception.SystemException;
import com.imadcn.framework.common.validate.RegexUtil;

/**
 * aes加密算法
 * 
 */
public class AESUtil {

	private static final Logger LOG = LoggerFactory.getLogger(AESUtil.class);

	private static final String CHARSET = "UTF-8";

	private static final String CIPHER_TYPE = "AES/ECB/PKCS5Padding";

	/**
	 * 加密
	 * 
	 * @param data 需要加密的数据
	 * @param key 加密秘钥
	 * @return
	 */
	public static String encryptStr(String data, String key) {
		byte[] result = encrypt(data, key);
		return EncodeUtil.byteToHexStr(result);
	}

	/**
	 * 解密
	 * 
	 * @param data 待解密的数据
	 * @param key 解密密钥
	 * @return
	 */
	public static String decryptStr(String data, String key) {
		byte[] dataBytes = EncodeUtil.hexStrToByte(data);
		byte[] decryptResult = decrypt(dataBytes, key);
		return new String(decryptResult);
	}
	
	/**
	 * 加密
	 * 
	 * @param data 需要加密的数据
	 * @param key 加密秘钥(长度必须为16)
	 * @return
	 */
	public static byte[] encrypt(String data, String key) {
		if (data == null) {
			throw new SystemException("Data is null");
		}

		if (RegexUtil.isEmpty(key) || key.length() != 16) {
			throw new SystemException("Key is null or key's length is not 16");
		}

		try {
			byte[] dataBytes = data.getBytes(CHARSET);
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] thedigest = md.digest(key.getBytes(CHARSET));
			SecretKeySpec secretKeySpec = new SecretKeySpec(thedigest, "AES");
			Cipher cipher = Cipher.getInstance(CIPHER_TYPE);
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
			byte[] result = new byte[cipher.getOutputSize(dataBytes.length)];
			int ctLength = cipher.update(dataBytes, 0, dataBytes.length, result, 0);
			ctLength += cipher.doFinal(result, ctLength);
			return result;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException("encrypt error", e);
		}
	}

	/**
	 * 解密
	 * 
	 * @param dataBytes 待解密的数据
	 * @param key 解密密钥(长度必须为16)
	 * @return
	 */
	public static byte[] decrypt(byte[] dataBytes, String key) {
		if (dataBytes == null) {
			throw new SystemException("Data is null");
		}

		if (RegexUtil.isEmpty(key) || key.length() != 16) {
			throw new SystemException("Key is null or key's length is not 16");
		}

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] thedigest = md.digest(key.getBytes(CHARSET));
			SecretKeySpec secretKeySpec = new SecretKeySpec(thedigest, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
			byte[] result = cipher.doFinal(dataBytes);
			return result;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new SystemException("decrypt error", e);
		}
	}

}
