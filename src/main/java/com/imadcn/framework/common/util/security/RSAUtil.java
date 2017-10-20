package com.imadcn.framework.common.util.security;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

public class RSAUtil {

	public static final String PUBLIC_KEY = "RSAPublicKey";

	public static final String PRIVATE_KEY = "RSAPrivateKey";

	private static final String KEY_ALGORITHM = "RSA";

	private static final Integer KEY_SIZE = 1024;

	private static final int MAX_ENCRYPT_BLOCK = 117;

	private static final int MAX_DECRYPT_BLOCK = 128;

	private static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	/**
	 * 初始化密钥, 即生成公钥和私钥
	 * 
	 * @throws NoSuchAlgorithmException
	 * 
	 */
	public static HashMap<String, Key> initKeys() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(KEY_SIZE);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		HashMap<String, Key> map = new HashMap<String, Key>();
		map.put(PUBLIC_KEY, publicKey);
		map.put(PRIVATE_KEY, privateKey);
		return map;
	}

	/**
	 * 取得私钥(返回结果使用base64进行了编码)
	 * 
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPrivateKey(Map<String, Key> keyMap) throws Exception {
		Key key = keyMap.get(PRIVATE_KEY);
		byte[] keyByte = key.getEncoded();
		return EncodeUtil.base64Encode(keyByte);
	}

	/**
	 * 取得公钥 (返回结果使用base64进行了编码)
	 * 
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPublicKey(Map<String, Key> keyMap) throws Exception {
		Key key = keyMap.get(PUBLIC_KEY);
		byte[] keyBytes = key.getEncoded();
		return EncodeUtil.base64Encode(keyBytes);
	}

	/**
	 * 公钥加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptByPublicKeyStr(String data, String key) throws Exception {
		byte[] dataBytes = data.getBytes();
		byte[] result = encryptByPublicKey(dataBytes, key);
		return EncodeUtil.base64Encode(result);
	}

	/**
	 * 公钥加密
	 * 
	 * @param data
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
		byte[] keyBytes = EncodeUtil.base64Decode(publicKey);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicK);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;

		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}

			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}

		byte[] encryptedData = out.toByteArray();
		out.close();
		return encryptedData;
	}

	/**
	 * 私钥加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptByPrivateKeyStr(String data, String key) throws Exception {
		byte[] dataBytes = data.getBytes();
		byte[] result = encryptByPrivateKey(dataBytes, key);
		return EncodeUtil.base64Encode(result);
	}

	/**
	 * 私钥加密
	 * 
	 * @param data
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, String privateKey) throws Exception {
		byte[] keyBytes = EncodeUtil.base64Decode(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateK);
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;

		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}

			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}

		byte[] encryptedData = out.toByteArray();
		out.close();
		return encryptedData;
	}

	/**
	 * 公钥解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String decryptByPublicKeyStr(String data, String key) throws Exception {
		byte[] dataBytes = EncodeUtil.base64Decode(data);
		byte[] result = decryptByPublicKey(dataBytes, key);
		return EncodeUtil.base64Encode(result);
	}

	/**
	 * 公钥解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) throws Exception {
		byte[] keyBytes = EncodeUtil.base64Decode(publicKey);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicK);
		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}

	/**
	 * 私钥解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String decryptByPrivateKeyStr(String data, String key) throws Exception {
		byte[] dataBytes = EncodeUtil.base64Decode(data);
		byte[] result = decryptByPrivateKey(dataBytes, key);
		return new String(result);
	}

	/**
	 * 私钥解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) throws Exception {
		byte[] keyBytes = EncodeUtil.base64Decode(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateK);
		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}

	/**
	 * 用私钥生成数字签名
	 * 
	 * @param data
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static String sign(byte[] data, String privateKey) throws Exception {
		byte[] keyBytes = EncodeUtil.base64Decode(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(privateK);
		signature.update(data);
		return EncodeUtil.base64Encode(signature.sign());
	}

	/**
	 * 校验数字签名
	 * 
	 * @param data
	 * @param publicKey
	 * @param sign
	 * @return
	 * @throws Exception
	 */
	public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
		byte[] keyBytes = EncodeUtil.base64Decode(publicKey);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PublicKey publicK = keyFactory.generatePublic(keySpec);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(publicK);
		signature.update(data);
		return signature.verify(EncodeUtil.base64Decode(sign));
	}

	public static void main(String[] args) throws Exception {
		Map<String, Key> keyMap = RSAUtil.initKeys();

		String publicKey = RSAUtil.getPublicKey(keyMap);
		String privateKey = RSAUtil.getPrivateKey(keyMap);
		publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCHwngvMKn2LnYu7MJxmRleoiJz9Oro7uN8SP8+MmP8CoTHdeoa4UCufUHSGwYjYFfDPPm6mjjmwR7BUg7+WgB4jW7ubLVQpzN7ku03RJlAg4BpPOJh4dCMrMYapOB0RIPyS9PdhZ5yGvkeq3kCuuSZwTk1Hpt0whnLwmCzn18FkQIDAQAB";
		System.err.println("公钥: \n" + publicKey);
		System.err.println("私钥： \n" + privateKey);

		String data = "123456abcdefghij";
		String data1 = RSAUtil.encryptByPublicKeyStr(data, publicKey);
		System.out.println("公钥加密前:\n" + data);
		System.out.println("公钥加密后：\n" + data1);

		String data2 = RSAUtil.decryptByPrivateKeyStr(data1, privateKey);
		System.out.println("私钥解密前:\n" + data1);
		System.out.println("私钥解密后:\n" + data2);
	}
}
