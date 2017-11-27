package com.imadcn.framework.common.validate;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.imadcn.framework.common.identity.IDCardUtil;


/**
 * 正则表达式，数据验证工具
 * @author iMad
 * @since 20130811.1158
 * @version 1.0
 */
public class RegexUtil {
	
	/**
	 * 判断邮件地址是否正确
	 * @param address 邮件地址
	 * @return 正确返回true，否则返回false
	 */
	public static boolean isEmail(String address) {
		if (address == null || "".equals(address) || address.indexOf("..") != -1) //地址不为null, 不为"",不能有两个连续的.
			return false;
		String regex = "[a-zA-Z0-9][\\w-.]+[a-zA-Z0-9]@[a-zA-Z0-9][\\w-.]+[a-zA-Z]"; // 匹配正则表达式
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(address);
		return matcher.matches();
	}
	
	/**
	 * 判断手机号码(11位)是否正确
	 * @param cellphone 手机号
	 * @return 正确返回true，否则返回false
	 */
	public static boolean isCellphone(String cellphone) {
		/*
		 * 移动号段：134~139，147，150~152，157~159，181~183， 187~188，178
		 * 联通号段：130~132，155~156，185~186，176
		 * 电信号段：133，153，180，181，189，177，173
		 * 虚拟运营：170，171
		 * 13[0,1,2,3,4,5,6,7,8,9]
		 * 147
		 * 15[0,1,2,3,5,6,7,8,9]
		 * 18[0,1,2,3,5,6,7,8,9]
		 */
		if (cellphone == null || "".equals(cellphone))
			return false;
		String regex = "^1([34578][0-9])[0-9]{8}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(cellphone);
		return matcher.matches();
	}
	
	/**
	 * 判断是否为金额数字类型，区间[0,+oo)，除小于1的小数外，首位不能为0，请小数点最多保留两位
	 * @param moeny 金额字符串
	 * @return 正确返回true，否则返回false
	 */
	public static boolean isMoney(String moeny) {
		if (moeny == null || "".equals(moeny))
			return false;
		String regex = "^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(moeny);
		return matcher.matches();
	}
	
	/**
	 * 判断IP地址是否正确
	 * @param ip ip
	 * @return 正确返回true，否则返回false
	 */
	public static boolean isIpAddrress(String ip) {
		if (ip == null || "".equals(ip))
			return false;
		// String regex = "^1[3458][0-9]{9}$"; // 手机号匹配正则表达式
		String regex = "((25[0-5]|2[0-4]\\d|1?\\d?\\d)\\.){3}(25[0-5]|2[0-4]\\d|1?\\d?\\d)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(ip);
		return matcher.matches();
	}
	
	/**
	 * 批量判断是否<b> 全部不为 </b> null 或者 ""
	 * @param values 待判断的字符串数组
	 * @return 是返回true，否则返回false
	 */
	public static boolean isNotEmpty(String... values) {
		if (values != null && values.length > 0) {
			for (String val : (String[]) values) {
				if (val == null || val.isEmpty()) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 批量判断是否<b> 全部不为 </b> null 或者 ""
	 * @param values 待判断的字符串数组
	 * @return 是返回true，否则返回false
	 */
	public static boolean isNotNull(String... values) {
		if (values != null && values.length > 0) {
			for (String val : (String[]) values) {
				if (val == null) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
		
	/** 
	 * 判断字符串是否<b> 包含 </b> null 或者 ""
	 * @param values 待判断的字符串
	 * @return 是返回true，否则返回false
	 */
	public static boolean isEmpty(String... values) {
		if (values != null && values.length > 0) {
			for (String val : values) {
				if (val == null || val.isEmpty()) {
					return true;
				}
			}
			return false;
		}
		return true;
	}
	
	/**
	 * 判断对象是否含有null
	 * @param values 待判断的对象
	 * @return 是返回true，否则返回false
	 */
	public static boolean isNull(Object... values) {
		if (values != null && values.length > 0) {
			for (Object val : values) {
				if (val == null) {
					return true;
				}
			}
			return false;
		}
		return true; 
	}
	
	/** 
	 * 判断字符串是否<b> 全部为 </b> null 或者 ""
	 * @param values 待判断的字符串
	 * @return 是返回true，否则返回false
	 */
	public static boolean isAllEmpty(String... values) {
		if (values != null && values.length > 0) {
			int count = 0;
			for (String val : values) {
				if (val == null || val.isEmpty()) {
					count++;
				}
			}
			return count == values.length;
		}
		return true;
	}
	
	/** 
	 * 判断字符串是否<b> 包含 </b> null 或者 ""
	 * @param values 待判断的字符串
	 * @return 是返回true，否则返回false
	 */
	public static boolean isEmpty(List<String> values) {
		if (values != null && !values.isEmpty()) {
			for (String val : values) {
				if (val == null || val.isEmpty()) {
					return true;
				}
			}
			return false;
		}
		return true;
	}
	
	public static boolean isInteger(String... values) {
		if (values != null && values.length > 0) {
			for (String v : values) {
				try {
					Integer.parseInt(v);
				} catch (Exception e) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * 验证是否为int
	 * @param values 待判断的字符串
	 * @return 是返回true，否则返回false
	 */
	public static boolean isPositiveInteger(String... values) {
		if (isInteger(values)) {
			for (String v : values) {
				if (Integer.parseInt(v) <= 0) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 判断是否为非负数
	 * @param values
	 * @return
	 */
	public static boolean isNotNegativeInteger(String... values) {
		if (isInteger(values)) {
			for (String v : values) {
				if (Integer.parseInt(v) < 0) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 判断是否为非负数
	 * @param value
	 * @return
	 */
	public static boolean isNotNegativeInteger(int... value) {
		if (value != null && value.length > 0) {
			for (int v : value) {
				if (v < 0) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 验证是否为int
	 * @param value 待判断的数据
	 * @return 是返回true，否则返回false
	 */
	public static boolean isPositiveInteger(int... value) {
		if (value != null && value.length > 0) {
			for (int v : value) {
				if (v <= 0) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 验证是否为long
	 * @param value 待判断的字符串
	 * @return 是返回true，否则返回false
	 */
	public static boolean isPositiveLong(String value) {
		try { 
			return Long.parseLong(value) > 0;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 验证是否为long
	 * @param value 待判断的字符串
	 * @return 是返回true，否则返回false
	 */
	public static boolean isPositiveLong(Long value) {
		try { 
			return value != null && value.longValue() > 0;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean isDouble(String... values) {
		if (values != null && values.length > 0) {
			for (String v : values) {
				try {
					Double.parseDouble(v);
				} catch (Exception e) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	public static boolean isNotNegativeDouble(double... values) {
		if (values != null && values.length > 0) {
			for (double v : values) {
				if (v < 0) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	public static boolean isNotNegativeDouble(String... values) {
		if (isDouble(values)) {
			for (String v : values) {
				if (Double.parseDouble(v) < 0) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 验证是否为double
	 * @param values 待判断的字符串
	 * @return 是返回true，否则返回false
	 */
	public static boolean isPositiveDouble(double... values) {
		if (values != null && values.length > 0) {
			for (double v : values) {
				if (v <= 0) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 验证是否为double
	 * @param values 待判断的字符串
	 * @return 是返回true，否则返回false
	 */
	public static boolean isPositiveDouble(String... values) {
		if (isDouble(values)) {
			for (String v : values) {
				if (Double.parseDouble(v) <= 0) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 验证是否为float
	 * @param value 待判断的字符串
	 * @return 是返回true，否则返回false
	 */
	public static boolean isPositiveFloat(String value) {
		try { 
			return Float.parseFloat(value) > 0;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 校验身份证号码是否正确
	 * @param value 身份证号
	 * @return 成功返回true，失败返回false
	 */
	public static boolean isIDCard(String value) {
		if (value == null || value.length() != 18) {
			return false;
		}
		try {
			if ("".equals(IDCardUtil.IDCardValidate(value))) {
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}
	
	/**
	 * 验证格式为XXXX,XXXX,XXXX的数据（XXXX的取值范围a-zA-Z0-9）
	 * @param data 
	 * @return
	 */
	public static boolean isSplitData(String data) {
		if (data == null || "".equals(data.trim()))
			return false;
		String regex = "^([a-zA-Z0-9]+,?)+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(data);
		return matcher.matches();
	}
	
	/**
	 * 判断是否为中文
	 * @param value
	 * @return
	 */
	public static boolean isChinese(String value) {
		if (value == null || "".equals(value))
			return false;
		String regex = "^[\u4e00-\u9fa5]+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}
	
	/**
	 * 判断元素是否<b>在</b>给定集合里面
	 * @param value 指定元素
	 * @param set 集合
	 * @return
	 */
	public static boolean isIn(String value, String[] set) {
		if (isEmpty(value) || isEmpty(set)) {
			throw new IllegalArgumentException("参数错误");
		}
		for (String cmp : set) {
			if (value.equals(cmp)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断元素是否<b>在</b>给定集合里面
	 * @param value 指定元素
	 * @param set 集合
	 * @return
	 */
	public static boolean isIn(int value, int[] set) {
		if (set == null || set.length == 0) {
			throw new IllegalArgumentException("参数错误");
		}
		for (int cmp : set) {
			if (value == cmp) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断元素是否<b>在</b>给定集合里面
	 * @param value 指定元素
	 * @param set 集合
	 * @return
	 */
	public static <T> boolean isIn(T value, T[] set) {
		if (value == null || set == null || set.length == 0) {
			throw new IllegalArgumentException("参数错误");
		}
		for (T cmp : set) {
			if (value.equals(cmp)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断元素是否<b>不在</b>给定集合里面
	 * @param value 指定元素
	 * @param set 集合
	 * @return
	 */
	public static boolean isNotIn(String value, String[] set) {
		if (isEmpty(value) || isEmpty(set)) {
			throw new IllegalArgumentException("参数错误");
		}
		for (String cmp : set) {
			if (value.equals(cmp)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 判断元素是否<b>不在</b>给定集合里面
	 * @param value 指定元素
	 * @param set 集合
	 * @return
	 */
	public static boolean isNotIn(int value, int[] set) {
		if (set == null || set.length == 0) {
			throw new IllegalArgumentException("参数错误");
		}
		for (int cmp : set) {
			if (value == cmp) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 判断元素是否<b>不在</b>给定集合里面
	 * @param value 指定元素
	 * @param set 集合
	 * @return
	 */
	public static <T> boolean isNotIn(T value, T[] set) {
		if (value == null  || set == null || set.length == 0) {
			throw new IllegalArgumentException("参数错误");
		}
		for (T cmp : set) {
			if (value.equals(cmp)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 分，转换成两位小数的元
	 * @param fen
	 * @return
	 */
	public static String fenToYuan(int fen) {
		return new BigDecimal(fen).divide(new BigDecimal(100)).toString();
	}
	
	/**
	 * 拼接字符串，null作<b> "" </b>处理
	 * @param values
	 * @return
	 */
	public static String concat(String... values) {
		StringBuilder sb = new StringBuilder();
		if (values != null && values.length > 0) {
			for (String v : values) {
				if (v != null) {
					sb.append(v);
				}
			}
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		String[] objects = {"1", "2", "3"};
		String value = "4";
		System.out.println(isIn(value, objects));
	}
}
