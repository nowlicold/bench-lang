package com.bench.lang.base.exception;

import com.bench.lang.base.error.ErrorCode;
import com.bench.lang.base.error.enums.CommonErrorCodeEnum;
import com.bench.lang.base.error.enums.ErrorEnum;

/**
 * @author cold
 * @version $Id: DQRuntimeException.java,v 0.1 2009-5-21 上午01:15:32 cold Exp $
 */
public class BenchException extends Exception {

	protected ErrorCode errorCode;

	/**
	 * 
	 */
	private static final long serialVersionUID = -3095048051860067597L;

	/**
	 * 空构造器。
	 */
	public BenchException() {
		super();
	}

	public BenchException(String message, Throwable throwable) {
		this(CommonErrorCodeEnum.SYSTEM_ERROR, message, throwable);
	}

	public <E extends BenchException> BenchException(String message) {
		this(CommonErrorCodeEnum.SYSTEM_ERROR, message);
	}

	public BenchException(ErrorCode errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public BenchException(ErrorCode errorCode, Throwable throwable) {
		super(throwable);
		this.errorCode = errorCode;
	}

	public BenchException(ErrorCode errorCode, String message, Throwable throwable) {
		super(message, throwable);
		this.errorCode = errorCode;
	}

	/**
	 * 空构造器。
	 */
	public BenchException(ErrorCode errorCode) {
		super();
		this.errorCode = errorCode;
	}

	public <E extends BenchException> BenchException(E exception) {
		super(exception);
		this.errorCode = exception.getErrorCode();
	}

	public <E extends BenchException> BenchException(String message, E exception) {
		super(message, exception);
		this.errorCode = exception.getErrorCode();
	}

	public <E extends BenchRuntimeException> BenchException(E exception) {
		super(exception);
		this.errorCode = exception.getErrorCode();
	}

	public <E extends BenchRuntimeException> BenchException(String message, E exception) {
		super(message, exception);
		this.errorCode = exception.getErrorCode();
	}

	/**
	 * @see java.lang.Throwable#toString()
	 */
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("errorCode =").append(errorCode).append(",").append(super.toString());
		return buf.toString();
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public BenchException(ErrorEnum errorEnum, String message) {
		this(errorEnum.errorCode(), message);
	}

	public BenchException(ErrorEnum errorEnum, Throwable throwable) {
		this(errorEnum.errorCode(), throwable);
	}

	public BenchException(ErrorEnum errorEnum, String message, Throwable throwable) {
		this(errorEnum.errorCode(), message, throwable);
	}

	public BenchException(ErrorEnum errorEnum) {
		this(errorEnum.errorCode());
	}

}
