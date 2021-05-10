package com.bench.lang.base.error.enums;

import com.bench.lang.base.enums.EnumBase;
import com.bench.lang.base.error.ErrorCode;

/**
 * 错误枚举
 * 
 * @author cold
 * @version $Id: ErrorEnum.java,v 0.1 2008-12-30 上午10:22:20 cold Exp $
 */
public interface ErrorEnum extends EnumBase {

	/**
	 * 是否相等
	 * 
	 * @param errorCode
	 * @return
	 */
	public default boolean equals(ErrorCode errorCode) {
		return this.name().equals(errorCode.getName());
	}

	public default ErrorCode errorCode() {
		return new ErrorCode(name(), message());
	}
}
