/**
 * 
 */
package com.imadcn.framework.common.util.net;

import javax.servlet.http.HttpServletRequest;

import com.imadcn.framework.common.util.validate.RegexUtil;

/**
 * URL解析工具
 * 
 */
public class URLUtil {

	/**
	 * 获取没有后缀的uri
	 * 
	 * @param uri
	 * @return
	 */
	public static final String getURIWithoutSuffix(String uri) {
		if (uri == null || "".equals(uri.trim())) {
			return uri;
		}

		int pointIndex = uri.indexOf(".");
		if (pointIndex == -1) {
			return uri;
		}

		return uri.substring(0, pointIndex);
	}

	/**
	 * 获取uri的后缀
	 * 
	 * @param uri
	 * @return
	 */
	public static final String getURISuffix(String uri) {
		if (uri == null || "".equals(uri.trim())) {
			return uri;
		}

		int pointIndex = uri.indexOf(".");
		if (pointIndex == -1) {
			return "";
		}

		return uri.substring(pointIndex);
	}

	/**
	 * 获取没有后缀的uri
	 * 
	 * @param url
	 * @param contextPath
	 * @return
	 */
	public static final String getURIWithoutSuffix(String url, String contextPath) {
		if (url == null || "".equals(url.trim())) {
			return url;
		}

		int contextPathPonit = url.indexOf(contextPath);

		if (contextPathPonit != -1) {
			url = url.substring(contextPathPonit);
		}

		int pointIndex = url.indexOf(".");

		if (pointIndex == -1) {
			return url;
		} else {
			return url.substring(0, pointIndex);
		}
	}

	/**
	 * 是否是json请求
	 * 
	 * @return
	 */
	public static final boolean isAjaxUrl(HttpServletRequest request) {
		String accept = request.getHeader("Accept");

		if (RegexUtil.isNotEmpty(accept) && (accept.contains("application/json") || accept.contains("application/jsonp"))) {
			return true;
		}

		String uri = request.getRequestURI();
		int suffixIndex = uri.lastIndexOf(".");

		if (suffixIndex != -1) {
			String suffix = uri.substring(suffixIndex + 1);
			if ("json".equals(suffix) || "jsonp".equals(suffix)) {
				return true;
			}
		}

		String format = request.getParameter("format");

		if ("json".equals(format) || "jsonp".equals(format)) {
			return true;
		}

		String ajaxHeader = request.getHeader("X-Requested-With");

		if (RegexUtil.isNotEmpty(ajaxHeader) && "XMLHttpRequest".equalsIgnoreCase(ajaxHeader)) {
			return true;
		}

		return false;
	}

	/**
	 * 是否是jsonp请求
	 * 
	 * @return
	 */
	public static final boolean isJsonp(HttpServletRequest request) {
		String accept = request.getHeader("Accept");

		if (RegexUtil.isNotEmpty(accept) && (accept.contains("application/jsonp"))) {
			return true;
		}

		String format = request.getParameter("format");

		if ("jsonp".equals(format)) {
			return true;
		}

		String uri = request.getRequestURI();
		int suffixIndex = uri.lastIndexOf(".");

		if (suffixIndex != -1) {
			String suffix = uri.substring(suffixIndex + 1);
			if ("jsonp".equals(suffix)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 获取用户访问地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getClientAddr(HttpServletRequest request) {
		String str = request.getHeader("X-Forwarded-For");

		if ((str == null) || (str.length() == 0) || ("unknown".equalsIgnoreCase(str))) {
			str = request.getHeader("X-Real-IP");
		}
		if (str == null || str.length() == 0 || "unknown".equalsIgnoreCase(str)) {
			str = request.getHeader("Proxy-Client-IP");
		}
		if (str == null || str.length() == 0 || "unknown".equalsIgnoreCase(str)) {
			str = request.getHeader("WL-Proxy-Client-IP");
		}
		if (str == null || str.length() == 0 || "unknown".equalsIgnoreCase(str)) {
			str = request.getHeader("HTTP_CLIENT_IP");
		}
		if (str == null || str.length() == 0 || "unknown".equalsIgnoreCase(str)) {
			str = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (str == null || str.length() == 0 || "unknown".equalsIgnoreCase(str)) {
			str = request.getRemoteAddr();
		}

		if (str != null && str.indexOf(",") != -1) {
			str = str.substring(str.lastIndexOf(",") + 1, str.length()).trim();
		}

		return String.valueOf(str);
	}

	public static String getHttp(String url) {
		String http = "";

		if (url.indexOf("http://") != -1) {
			http = "http://";
		} else if (url.indexOf("https://") != -1) {
			http = "https://";
		}

		return http;
	}

}