/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.money.converter;

import com.bench.lang.base.money.Money;
import com.bench.lang.base.string.converter.ToStringConverter;

/**
 * 金额转换为String的转换器
 * 
 * @author cold
 *
 * @version $Id: MoneyToStringConverter.java, v 0.1 2020年4月9日 上午9:17:16 cold Exp $
 */
public class MoneyToStringConverter implements ToStringConverter<Money> {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bench.common.lang.ToStringConverter#convert(java.lang.Object)
	 */
	@Override
	public String convert(Money t) {
		// TODO Auto-generated method stub
		return t == null ? null : t.toSimpleString();
	}
}
