/**
 * BenchCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.bench.lang.base.json.value.serializer.impl;


import com.bench.lang.base.bool.utils.BooleanUtils;
import com.bench.lang.base.json.value.serializer.JSONSerializer;

/**
 * 返回Int类型的Boolean值，true为1，false为0
 * 
 * @author cold
 * 
 * @version $Id: BooleanToIntConverter.java, v 0.1 2012-6-25 下午12:31:14 cold
 *          Exp $
 */
public class BooleanToIntSerializer implements JSONSerializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bench.common.json.converter.JSONConverter#convertObjectValue(java
	 * .lang.String, java.lang.Object, java.lang.reflect.Field,
	 * com.bench.common.json.JsonConfig)
	 */
	@Override
	public Object serialize(Object value) {
		// TODO Auto-generated method stub
		if (value == null) {
			return null;
		}
		Boolean booleanValue = BooleanUtils.toBoolean(value);
		if (booleanValue)
			return 1;
		else
			return 0;
	}

}
