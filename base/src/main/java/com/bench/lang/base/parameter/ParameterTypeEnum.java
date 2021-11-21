/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.parameter;

import com.bench.common.enums.EnumBase;

/**
 * 
 * @author cold
 * 
 * @version $Id: ParameterTypeEnum.java, v 0.1 2013-1-3 下午7:41:22 cold Exp $
 */
public enum ParameterTypeEnum implements EnumBase {

	COMMON("普通值，如字符串，数字"),

	PROPERTIES("KV值"),

	VELOCITY("VELOCITY脚本"),

	JSON("JSON值");

	private String message;

	private ParameterTypeEnum(String message) {
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
		return null;
	}

}
