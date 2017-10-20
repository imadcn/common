package com.imadcn.framework.common.errorcode;

public enum ErrorCodeEnum implements ErrorCode {
	COMMON_SUCCESS(0, "success"),
	FTP_EXCEPTION(10_0001, "ftp error");

	private int code;
	private String desc;

	private ErrorCodeEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	/**
	 * 根据code匹配结果
	 * @param code code
	 * @return ErrorCodeEnum
	 */
	public static ErrorCodeEnum get(int code) {
		ErrorCodeEnum[] v = values();
		if (v != null && v.length > 0) {
			for (ErrorCodeEnum o : v) {
				if (o.getCode() == code) {
					return o;
				}
			}
		}
		return null;
	}

}
