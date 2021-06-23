package com.bench.lang.base.error.enums;

import com.bench.lang.base.error.ErrorCode;

/**
 * 通用的HTTP错误
 * 
 * @author cold
 * @version $Id: CommonHttpErrorCodeEnum.java,v 0.1 2009-7-23 下午09:07:49 cold Exp $
 */
public enum CommonHttpErrorCodeEnum implements ErrorEnum {

	HTTP_METHOD_NOT_SUPPORT("HTTP方法不支持"),

	UPLOAD_FILE_TOO_LARGE("上传文件过大"),

	FORM_DUPLICATE_SUBMIT("表单重复提交"),

	FORM_DATA_JUGGLED("表单数据被篡改"),

	FORM_TOKEN_REQUIRED("表单的token不能为空"),

	CSRF_TOKEN_MISSING("csrf token缺失"),

	CSRF_TOKEN_INVALID("csrf token不正确"),

	;

	private String message;

	private CommonHttpErrorCodeEnum(String message) {
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
