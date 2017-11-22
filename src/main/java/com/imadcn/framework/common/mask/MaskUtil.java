package com.imadcn.framework.common.mask;

/**
 * 字符串打码工具
 * @author yangchao
 * @since 2017-11-22
 */
public class MaskUtil {
	
	/**
	 * 给手机号中间4位打码成 *
	 * @param phoneNo
	 * @return
	 */
	public static String maskMiddle4(String phoneNo) {
		String regex = "(\\d{3})\\d{4}(\\d{4})";
		return phoneNo.replaceAll(regex, "$1****$2");
	}
}
