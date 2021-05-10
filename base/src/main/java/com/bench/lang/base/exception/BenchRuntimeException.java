package com.bench.lang.base.exception;

import com.bench.lang.base.error.ErrorCode;
import com.bench.lang.base.error.enums.CommonErrorCodeEnum;
import com.bench.lang.base.error.enums.ErrorEnum;

/**
 * @author cold
 * @version $Id: BenchRuntimeException.java,v 0.1 2009-5-21 上午01:15:32 cold Exp $
 */
public class BenchRuntimeException extends RuntimeException {

	protected ErrorCode errorCode;

	/**
	 * 
	 */
	private static final long serialVersionUID = -3095048051860067597L;

	/**
	 * 空构造器。
	 */
	public BenchRuntimeException() {
		super();
	}

	public BenchRuntimeException(String message, Throwable throwable) {
		this(CommonErrorCodeEnum.SYSTEM_ERROR, message, throwable);
	}

	public <E extends BenchException> BenchRuntimeException(String message) {
		this(CommonErrorCodeEnum.SYSTEM_ERROR, message);
	}

	public BenchRuntimeException(ErrorCode errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public BenchRuntimeException(ErrorCode errorCode) {
		super();
		this.errorCode = errorCode;
	}

	public BenchRuntimeException(ErrorCode errorCode, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
	}

	public BenchRuntimeException(ErrorCode errorCode, String message, Throwable throwable) {
		super(message, throwable);
		this.errorCode = errorCode;
	}

	public <E extends BenchException> BenchRuntimeException(E exception) {
		super(exception);
		this.errorCode = exception.getErrorCode();
	}

	public <E extends BenchException> BenchRuntimeException(String message, E exception) {
		super(message, exception);
		this.errorCode = exception.getErrorCode();
	}

	public <E extends BenchRuntimeException> BenchRuntimeException(E exception) {
		super(exception);
		this.errorCode = exception.getErrorCode();
	}

	public <E extends BenchRuntimeException> BenchRuntimeException(String message, E exception) {
		super(message, exception);
		this.errorCode = exception.getErrorCode();
	}

	/**
	 * @see java.lang.Throwable#toString()
	 */
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("errorCode=").append(errorCode).append(",").append(super.toString());
		return buf.toString();
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public BenchRuntimeException(ErrorEnum errorEnum, String message) {
		this(errorEnum.errorCode(), message);
	}

	/**
	 * 空构造器。
	 */
	public BenchRuntimeException(ErrorEnum errorEnum) {
		this(errorEnum.errorCode());
	}

	/**
	 * 空构造器。
	 */
	public BenchRuntimeException(ErrorEnum errorEnum, Throwable cause) {
		this(errorEnum.errorCode(), cause);
	}

	public BenchRuntimeException(ErrorEnum errorEnum, String message, Throwable throwable) {
		this(errorEnum.errorCode(), message, throwable);
	}

}
