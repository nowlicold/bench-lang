package com.bench.lang.base.error.enums;

import com.bench.lang.base.error.ErrorCode;

/**
 * 通用的第三方错误
 * 
 * @author cold
 * @version $Id: CommonThirdErrorCodeEnum.java,v 0.1 2009-7-23 下午09:07:49 cold Exp $
 */
public enum CommonThirdErrorCodeEnum implements ErrorEnum {

	THIRD_SERVER_SIGN_VERIFY_ERROR("验证第三方服务器签名失败"),

	THIRD_SYSTEM_ERROR_UNKNOWN("第三方系统错误未知"),

	WAIT_THIRD_RESULT_TIMEOUT("等待第三方结果超时"),

	THIRD_SYSTEM_ERROR("第三方系统错误"),;

	private String message;

	private CommonThirdErrorCodeEnum(String message) {
		this.message = message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.lang.base.enums.EnumBase#message()
	 */
	public String message() {
		// TODO Auto-generated method stub
		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.lang.base.enums.EnumBase#value()
	 */
	public Number value() {
		// TODO Auto-generated method stub
		return 0;
	}

	public ErrorCode toErrorCode() {
		return new ErrorCode(this.name(), this.message);
	}

}
