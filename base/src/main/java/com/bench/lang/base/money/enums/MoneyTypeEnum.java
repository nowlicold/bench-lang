/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.money.enums;

import com.bench.common.enums.EnumBase;

/**
 * 金额类型
 * 
 * @author cold
 * 
 * @version $Id: MoneyTypeEnum.java, v 0.1 2014-3-26 上午11:32:00 cold Exp $
 */
public enum MoneyTypeEnum implements EnumBase {

	NORMAL("普通金额，精确到分"),

	RMB("人民币金额，精确到毫厘");

	private String message;

	private MoneyTypeEnum(String message) {
		this.message = message;
	}

	@Override
	public String message() {
		// TODO Auto-generated method stub
		return message;
	}

	@Override
	public Number value() {
		// TODO Auto-generated method stub
		return null;
	}

}
