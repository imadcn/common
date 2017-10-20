package com.imadcn.framework.common.exception;

import com.imadcn.framework.common.errorcode.ErrorCode;

/**
 * 业务异常类
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -6971716908203238516L;

	private int code;
	
	public BusinessException() {
		super();
	}
	
	public BusinessException(String message) {
		super(message);
	}
	
	public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
	
	public BusinessException(Throwable cause) {
        super(cause);
    }

	public BusinessException(int code, String message) {
		super(message);
		this.code = code;
	}

	public BusinessException(ErrorCode errorCode) {
		super(errorCode.getDesc());
		this.code = errorCode.getCode();
	}

	public int getCode() {
		return code;
	}

}
