package com.imadcn.framework.common.net;

import javax.servlet.http.HttpServletRequest;

/**
 * UserAgent Util
 * 
 * @author yc
 * @since 2017-01-10
 */
public class UserAgentUtil {

	/**
	 * 判断是否在微信环境打开
	 * 
	 * @param req HttpServletRequest
	 * @return
	 */
	public static boolean isInnerWechat(HttpServletRequest req) {
		String ua = req.getHeader("user-agent");
		if (ua != null && ua.toLowerCase().contains("micromessenger")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否在【支付宝】环境中打开
	 * 
	 * @param req HttpServletRequest
	 * @return
	 */
	public static boolean isInnerAlipay(HttpServletRequest req) {
		String ua = req.getHeader("user-agent");
		if (ua != null && ua.toLowerCase().contains("alipay")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取用户的真实IP
	 * 
	 * @param req HttpServletRequest
	 * @return
	 */
	public static String getUserRealIp(HttpServletRequest req) {
		String ipString = "";
		if (req.getHeader("x-forwarded-for") == null) {
			ipString = req.getRemoteAddr();
		} else {
			ipString = req.getHeader("x-forwarded-for");
		}
		if (ipString.indexOf(",") != -1) {
			String[] iparr = ipString.replaceAll(" ", "").split(",");
			ipString = iparr[0];
		}
		return ipString;
	}
}
