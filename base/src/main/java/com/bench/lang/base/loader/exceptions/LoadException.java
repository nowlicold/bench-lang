/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.loader.exceptions;

import com.yuan.common.enums.error.CommonErrorCodeEnum;
import com.yuan.common.enums.error.ErrorEnum;
import com.yuan.common.error.ErrorCode;
import com.yuan.common.exception.BenchException;
import com.yuan.common.exception.BenchRuntimeException;

/**
 * 加载异常
 * 
 * @author cold
 *
 * @version $Id: LoadException.java, v 0.1 2020年5月19日 下午3:13:03 cold Exp $
 */
public class LoadException extends BenchException {

	/** 
	 * 
	 */
	private static final long serialVersionUID = -3933627621848152625L;

	/**
	 * 
	 */
	public LoadException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param throwable
	 */
	public LoadException(String message, Throwable throwable) {
		super(CommonErrorCodeEnum.ERROR_UNKNOWN,message, throwable);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param <E>
	 * @param message
	 */
	public <E extends BenchException> LoadException(String message) {
		super(CommonErrorCodeEnum.ERROR_UNKNOWN,message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param errorCode
	 * @param message
	 */
	public LoadException(ErrorCode errorCode, String message) {
		super(errorCode, message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param errorCode
	 * @param throwable
	 */
	public LoadException(ErrorCode errorCode, Throwable throwable) {
		super(errorCode, throwable);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param errorCode
	 * @param message
	 * @param throwable
	 */
	public LoadException(ErrorCode errorCode, String message, Throwable throwable) {
		super(errorCode, message, throwable);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param errorCode
	 */
	public LoadException(ErrorCode errorCode) {
		super(errorCode);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param <E>
	 * @param exception
	 */
	public <E extends BenchException> LoadException(E exception) {
		super(exception);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param <E>
	 * @param message
	 * @param exception
	 */
	public <E extends BenchException> LoadException(String message, E exception) {
		super(message, exception);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param <E>
	 * @param exception
	 */
	public <E extends BenchRuntimeException> LoadException(E exception) {
		super(exception);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param <E>
	 * @param message
	 * @param exception
	 */
	public <E extends BenchRuntimeException> LoadException(String message, E exception) {
		super(message, exception);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param errorEnum
	 * @param message
	 */
	public LoadException(ErrorEnum errorEnum, String message) {
		super(errorEnum, message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param errorEnum
	 * @param throwable
	 */
	public LoadException(ErrorEnum errorEnum, Throwable throwable) {
		super(errorEnum, throwable);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param errorEnum
	 * @param message
	 * @param throwable
	 */
	public LoadException(ErrorEnum errorEnum, String message, Throwable throwable) {
		super(errorEnum, message, throwable);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param errorEnum
	 */
	public LoadException(ErrorEnum errorEnum) {
		super(errorEnum);
		// TODO Auto-generated constructor stub
	}

}
