package com.bench.lang.base.error.enums;

import com.bench.lang.base.error.ErrorCode;

/**
 * @author cold
 * @version $Id: CommonErrorCodeEnum.java,v 0.1 2009-7-23 下午09:07:49 cold Exp $
 */
public enum CommonErrorCodeEnum implements ErrorEnum {

	SYSTEM_NOT_STARTED("系统未启动"),

	USER_NOT_LOGIN("用户未登录"),

	USER_PERMISSION_DENIED("访问被拒绝"),

	PARAMETER_ERROR("参数错误"),

	//
	ILLEGAL_MONEY_FORMAT("金额格式不正确"),
	//
	ILLEGAL_DATE_FORMAT("日期格式不正确"),
	//
	SYSTEM_ERROR("系统错误"),

	SYSTEM_CONFIG_ERROR("系统配置错误"),

	ERROR_UNKNOWN("未知错误"),

	SPRING_BEAN_IS_MISSING("缺少SpringBean"),

	URL_UNKNOWN("url未知"),

	//
	NO_DATA("没有数据");

	private String message;

	private CommonErrorCodeEnum(String message) {
		this.message = message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.common.enums.EnumBase#message()
	 */
	public String message() {
		// TODO Auto-generated method stub
		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.common.enums.EnumBase#value()
	 */
	public Number value() {
		// TODO Auto-generated method stub
		return 0;
	}

	public ErrorCode toErrorCode() {
		return new ErrorCode(this.name(), this.message);
	}

}
