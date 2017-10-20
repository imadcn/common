package com.imadcn.framework.common.exception;

import com.imadcn.framework.common.errorcode.ErrorCode;

/**
 * 业务异常类
 */
public class SystemException extends RuntimeException {

	private static final long serialVersionUID = -6971716908203238516L;

	private int code;
	
	public SystemException() {
		super();
	}
	
	public SystemException(String message) {
		super(message);
	}
	
	public SystemException(String message, Throwable cause) {
        super(message, cause);
    }
	
	public SystemException(Throwable cause) {
        super(cause);
    }

	public SystemException(int code, String message) {
		super(message);
		this.code = code;
	}

	public SystemException(ErrorCode errorCode) {
		super(errorCode.getDesc());
		this.code = errorCode.getCode();
	}

	public int getCode() {
		return code;
	}

}
