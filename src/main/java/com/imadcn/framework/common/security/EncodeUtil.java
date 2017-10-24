package com.imadcn.framework.common.security;

import org.apache.commons.codec.binary.Base64;

public class EncodeUtil {

	public static String encode(byte[] plain) {
		return byteToHexStr(plain);
	}

	/**
	 * 将16进制字符串转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] hexStrToByte(String hexStr) {
		if (hexStr == null) {
			return null;
		}

		byte[] result = new byte[hexStr.length() / 2];

		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}

		return result;
	}

	/**
	 * 将二进制转换成16进制字符串
	 * 
	 * @param buf
	 * @return
	 */
	public static String byteToHexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);

			if (hex.length() == 1) {
				hex = '0' + hex;
			}

			sb.append(hex);
		}

		return sb.toString();
	}

	public static String base64Encode(byte[] plain) {
		return new String(Base64.encodeBase64(plain));
	}

	public static byte[] base64Decode(String key) {  
	    return Base64.decodeBase64(key);  
	} 
}
