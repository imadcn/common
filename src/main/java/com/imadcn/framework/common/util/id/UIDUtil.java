package com.imadcn.framework.common.util.id;

import java.util.UUID;

/**
 * UIDUtil
 * @author yangchao
 * @since 2015-07-20
 */
public class UIDUtil {

	private static String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "_", "-" };

	/**
	 * 获得指定数目标UUID
	 * 
	 * @param number int 必需获得的UUID数量
	 * @return String[] UUID数组
	 */
	public static String[] uuid(int number) {
		if (number < 1) {
			return null;
		}
		String[] ss = new String[number];
		for (int i = 0; i < number; i++) {
			ss[i] = uuid();
		}
		return ss;
	}

	/**
	 * 6个字节的短UID
	 * 
	 * @return
	 */
	public static String uuid6() {
		return shortUid(6);
	}

	/**
	 * 8个字节的短UID
	 * @return
	 */
	public static String uuid8() {
		return shortUid(8);
	}
	
	private static String shortUid(int size) {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < size; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x3E]);
		}
		return shortBuffer.toString();
	}

	/**
	 * 标准UUID
	 * @return
	 */
	public static String uuid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 不带"-"符号的UUID
	 * @return
	 */
	public static String noneDashUuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	/**
	 * 64位[0-9a-zA-Z][_-]UUID
	 * 
	 * @return
	 */
	public static String uuid64Bit() {
		StringBuffer r = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		int index = 0;
		int[] buff = new int[3];
		int l = uuid.length();
		for (int i = 0; i < l; i++) {
			index = i % 3;
			buff[index] = Integer.parseInt("" + uuid.charAt(i), 16);
			if (index == 2) {
				r.append(chars[buff[0] << 2 | buff[1] >>> 2]);
				r.append(chars[(buff[1] & 3) << 4 | buff[2]]);
			}
		}
		return r.toString();
	}
}
