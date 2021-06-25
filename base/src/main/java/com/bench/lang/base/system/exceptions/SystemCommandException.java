/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.system.exceptions;

import com.bench.common.enums.error.ErrorEnum;
import com.bench.common.error.ErrorCode;
import com.bench.common.exception.BenchException;
import com.bench.common.exception.BenchRuntimeException;

/**
 * 系统命令错误
 * 
 * @author cold
 *
 * @version $Id: SystemCommandException.java, v 0.1 2016年11月11日 下午3:29:44 cold Exp $
 */
public class SystemCommandException extends BenchException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -213301528312482500L;

	public SystemCommandException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public <E extends BenchException> SystemCommandException(E exception) {
		super(exception);
		// TODO Auto-generated constructor stub
	}

	public <E extends BenchRuntimeException> SystemCommandException(E exception) {
		super(exception);
		// TODO Auto-generated constructor stub
	}

	public SystemCommandException(ErrorCode errorCode, String message, Throwable throwable) {
		super(errorCode, message, throwable);
		// TODO Auto-generated constructor stub
	}

	public SystemCommandException(ErrorCode errorCode, String message) {
		super(errorCode, message);
		// TODO Auto-generated constructor stub
	}

	public SystemCommandException(ErrorCode errorCode, Throwable throwable) {
		super(errorCode, throwable);
		// TODO Auto-generated constructor stub
	}

	public SystemCommandException(ErrorCode errorCode) {
		super(errorCode);
		// TODO Auto-generated constructor stub
	}

	public SystemCommandException(ErrorEnum errorEnum, String message, Throwable throwable) {
		super(errorEnum, message, throwable);
		// TODO Auto-generated constructor stub
	}

	public SystemCommandException(ErrorEnum errorEnum, String message) {
		super(errorEnum, message);
		// TODO Auto-generated constructor stub
	}

	public SystemCommandException(ErrorEnum errorEnum, Throwable throwable) {
		super(errorEnum, throwable);
		// TODO Auto-generated constructor stub
	}

	public SystemCommandException(ErrorEnum errorEnum) {
		super(errorEnum);
		// TODO Auto-generated constructor stub
	}

	public <E extends BenchException> SystemCommandException(String message, E exception) {
		super(message, exception);
		// TODO Auto-generated constructor stub
	}

	public <E extends BenchRuntimeException> SystemCommandException(String message, E exception) {
		super(message, exception);
		// TODO Auto-generated constructor stub
	}

}
