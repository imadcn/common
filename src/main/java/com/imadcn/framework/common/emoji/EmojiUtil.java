package com.imadcn.framework.common.emoji;

import org.apache.commons.lang.StringUtils;

public class EmojiUtil {

	/**
	 * 检测是否有emoji字符
	 *
	 * @param source
	 * @return 是：<b>true</b>，否：<b>false</b>
	 */
	public static boolean containsEmoji(String source) {
		if (StringUtils.isBlank(source)) {
			return false;
		}
		int len = source.length();
		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);
			if (isEmojiCharacter(codePoint)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断字符是否为emoji
	 * @param codePoint
	 * @return 是：<b>true</b>，否：<b>false</b>
	 */
	private static boolean isEmojiCharacter(char codePoint) {
		return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
				|| ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
	}

	/**
	 * 过滤emoji 或者 其他非文字类型的字符
	 *
	 * @param source
	 * @return 过滤emoji后的字符串
	 */
	public static String filterEmoji(String source) {
		source = source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "*");
		if (!containsEmoji(source)) {
			// 如果不包含，直接返回
			return source;
		}
		StringBuilder buf = null;
		int len = source.length();
		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);
			if (isEmojiCharacter(codePoint)) {
				if (buf == null) {
					buf = new StringBuilder(source.length());
				}
				buf.append(codePoint);
			} else {
				buf.append("*");
			}
		}
		if (buf == null) {
			// 如果没有找到 emoji表情，则返回源字符串
			return source;
		} else {
			if (buf.length() == len) {
				buf = null;
				return source;
			} else {
				return buf.toString();
			}
		}
	}
}
